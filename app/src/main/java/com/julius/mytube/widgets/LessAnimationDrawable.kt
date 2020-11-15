package com.julius.mytube.widgets

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.SparseArray
import java.util.concurrent.atomic.AtomicBoolean


class LessAnimationDrawable<T> private constructor(
    files: Array<T>,
    duration: Int,
    loader: ImageLoader<T>
) : AnimationDrawable() {
    private val drawables: SparseArray<PlaceHolderDrawable<T>>
    private val loader: ImageLoader<T>
    private val callback: Callback
    private val paused = AtomicBoolean()
    private val handler: Handler = Handler(Looper.getMainLooper())
    private val callbackProxy: Callback

    private var compensate: Long = 0

    companion object {
        fun <T> create(
            files: Array<T>,
            duration: Int,
            loader: ImageLoader<T>
        ): LessAnimationDrawable<T> {
            return LessAnimationDrawable(files, duration, loader)
        }
    }

    init {
        require(files.isNotEmpty()) { "files length <= 0" }
        require(duration > 0) { "duration <= 0(ms)" }
        this.loader = loader
        drawables = SparseArray()
        callback = object : Callback {
            override fun call(index: Int, drawable: Drawable?) {
                drawables[index].drawable = drawable
                if (paused.get()) {
                    paused.set(false)
                    run()
                } else {
                    start()
                }
            }
        }
        callbackProxy = object : Callback {
            override fun call(index: Int, drawable: Drawable?) {
                compensate = SystemClock.uptimeMillis() - compensate
                if (Thread.currentThread() === handler.looper.thread) {
                    callback.call(index, drawable)
                } else {
                    handler.post { callback.call(index, drawable) }
                }
            }
        }
        for (i in files.indices) {
            drawables.put(i, PlaceHolderDrawable.create(files[i], i))
            if (i > 0) {
                drawables[i].previous = drawables[i - 1]
                drawables[i - 1].next = drawables[i]
                if (i == files.size - 1) {
                    drawables[0].previous = drawables[i]
                    drawables[i].next = drawables[0]
                }
            } else {
                drawables[i].previous = drawables[i]
                drawables[i].next = drawables[i]
            }
            super.addFrame(drawables[i], duration)
        }
    }

    private fun load(index: Int, file: T) {
        compensate = SystemClock.uptimeMillis()
        loader.load(index, file, callbackProxy)
    }

    @Deprecated(
        "This method had been deprecated.",
        ReplaceWith("throw IllegalArgumentException(\"This method had been deprecated.\")")
    )
    override fun addFrame(frame: Drawable, duration: Int) {
        throw IllegalArgumentException("This method had been deprecated.")
    }

    override fun run() {
        if (drawables.size() >= 3) {
            if (current is PlaceHolderDrawable<*>) {
                val drawable = current as PlaceHolderDrawable<*>
                drawable.previous!!.previous!!.drawable = null
            }
        }
        if (current is PlaceHolderDrawable<*>) {
            val drawable = (current as PlaceHolderDrawable<*>).next
            if (drawable!!.drawable == null) {
                paused.set(true)
                @Suppress("UNCHECKED_CAST")
                load(drawable.index, drawable.file as T)
                return
            }
        }
        super.run()
    }

    override fun start() {
        if (drawables.size() > 0 && drawables[0].drawable == null) {
            load(0, drawables[0].file)
            return
        }
        super.start()
    }

    private class PlaceHolderDrawable<T> private constructor(val file: T, val index: Int) :
        Drawable() {
        private var _alpha: Int = -1
        private var _colorFilter: ColorFilter? = null

        var previous: PlaceHolderDrawable<T>? = null
        var next: PlaceHolderDrawable<T>? = null
        var drawable: Drawable? = null
            set(drawable) {
                field = drawable
                invalidateSelf()
            }

        override fun draw(canvas: Canvas) {
            val d = drawable
            if (d != null) {
                if (_alpha != -1) {
                    d.alpha = _alpha
                }
                if (_colorFilter != null) {
                    d.colorFilter = _colorFilter
                }
                d.bounds = bounds
                d.draw(canvas)
            }
        }

        override fun setAlpha(alpha: Int) {
            setAlpha(alpha)
            val d = drawable
            if (d != null) {
                d.alpha = alpha
                invalidateSelf()
            }
        }

        override fun setColorFilter(colorFilter: ColorFilter?) {
            _colorFilter = colorFilter
            val d = drawable
            if (d != null) {
                d.colorFilter = colorFilter
                invalidateSelf()
            }
        }

        override fun getOpacity(): Int {
            val d = drawable
            return d?.opacity ?: PixelFormat.TRANSPARENT
        }

        companion object {
            fun <T> create(file: T, index: Int): PlaceHolderDrawable<T> {
                return PlaceHolderDrawable(file, index)
            }
        }
    }

    interface ImageLoader<T> {
        fun load(index: Int, file: T, callback: Callback?)
    }

    interface Callback {
        fun call(index: Int, drawable: Drawable?)
    }
}