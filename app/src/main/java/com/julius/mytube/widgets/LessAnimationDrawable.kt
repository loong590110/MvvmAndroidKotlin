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


class LessAnimationDrawable private constructor(
    frameCount: Int,
    duration: Int,
    loader: ImageLoader
) : AnimationDrawable() {
    private val drawables: SparseArray<AnnulusDrawable>
    private val loader: ImageLoader
    private val callback: Callback
    private val callbackProxy: Callback
    private val paused = AtomicBoolean()
    private val handler = Handler(Looper.getMainLooper())

    private var compensate: Long = 0

    companion object {
        fun create(
            frameCount: Int,
            duration: Int,
            loader: ImageLoader
        ): LessAnimationDrawable {
            return LessAnimationDrawable(frameCount, duration, loader)
        }
    }

    init {
        require(frameCount > 0) { "frame count <= 0" }
        require(duration > 0) { "duration <= 0(ms)" }
        this.loader = loader
        drawables = SparseArray()
        callback = object : Callback {
            override fun call(index: Int, drawable: Drawable) {
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
            override fun call(index: Int, drawable: Drawable) {
                compensate = SystemClock.uptimeMillis() - compensate
                if (Thread.currentThread() === handler.looper.thread) {
                    callback.call(index, drawable)
                } else {
                    handler.post { callback.call(index, drawable) }
                }
            }
        }
        for (i in 0 until frameCount) {
            drawables.put(i, AnnulusDrawable.create(i))
            if (i > 0) {
                drawables[i].previous = drawables[i - 1]
                drawables[i - 1].next = drawables[i]
                if (i == frameCount - 1) {
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

    private fun load(index: Int) {
        compensate = SystemClock.uptimeMillis()
        loader.load(index, callbackProxy)
    }

    @Deprecated(
        message = "Deprecated", level = DeprecationLevel.ERROR, replaceWith =
        ReplaceWith("throw IllegalArgumentException(\"This method had been deprecated.\")")
    )
    override fun addFrame(frame: Drawable, duration: Int) {
        throw IllegalArgumentException("This method had been deprecated.")
    }

    override fun run() {
        if (drawables.size() >= 3) {
            if (current is AnnulusDrawable) {
                val drawable = current as AnnulusDrawable
                drawable.previous!!.previous!!.drawable = null
            }
        }
        if (current is AnnulusDrawable) {
            val drawable = (current as AnnulusDrawable).next
            if (drawable!!.drawable == null) {
                paused.set(true)
                load(drawable.index)
                return
            }
        }
        super.run()
    }

    override fun scheduleSelf(what: Runnable, `when`: Long) {
        super.scheduleSelf(what, `when` - compensate)
    }

    override fun start() {
        if (drawables.size() > 0 && drawables[0].drawable == null) {
            load(0)
            return
        }
        super.start()
    }

    private class AnnulusDrawable private constructor(val index: Int) :
        Drawable() {
        private var _alpha: Int = -1
        private var _colorFilter: ColorFilter? = null

        var previous: AnnulusDrawable? = null
        var next: AnnulusDrawable? = null
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
            fun create(index: Int): AnnulusDrawable {
                return AnnulusDrawable(index)
            }
        }
    }

    interface ImageLoader {
        fun load(index: Int, callback: Callback)
    }

    interface Callback {
        fun call(index: Int, drawable: Drawable)
    }
}