package com.julius.mytube.extends

import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import java.util.LinkedList
import kotlin.collections.LinkedHashMap

/**
 * Created by Developer Zailong Shi on 2020-01-07.
 */
operator fun Handler.invoke(block: () -> Unit) = post(block)
operator fun Handler.invoke(delay: Long, block: () -> Unit) = postDelayed(block, delay)
inline operator fun <T> T?.invoke(block: T.() -> Unit) = this?.apply(block)

//region ui handler
private val uiHandler by lazy { Handler(Looper.getMainLooper()) }
fun FragmentActivity.runOnUiThread(delay: Long, block: () -> Unit) = run { uiHandler(delay, block) }
fun Fragment.runOnUiThread(block: () -> Unit) = run { uiHandler(block) }
fun Fragment.runOnUiThread(delay: Long, block: () -> Unit) = run { uiHandler(delay, block) }
//endregion

//region dp to px
private typealias dp2px = (Float) -> Int

operator fun Float.invoke(dp: dp2px) = dp(this)
operator fun Int.invoke(dp: dp2px) = dp(toFloat())
val FragmentActivity.dp get() = dp2px(resources)
val Fragment.dp get() = dp2px(resources)
private fun dp2px(resources: Resources?) = { dp: Float ->
    resources?.run {
        TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics
        ).toInt()
    } ?: 0
}
//endregion

//region event bus
data class MessageSubscriber(val type: Any, val subscriber: (Any) -> Unit)

private val lifecycleObserver by lazy {
    object : LifecycleObserver {
        val subscribersOwner = LinkedHashMap<LifecycleOwner, LinkedList<MessageSubscriber>>()
        val subscribers = LinkedHashMap<Any, LinkedList<MessageSubscriber>>()

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onUnsubscribe(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            subscribersOwner.let { it ->
                it[owner] {
                    forEach { subscriber ->
                        subscribers.let {
                            it[subscriber.type] {
                                remove(subscriber)
                                if (size == 0) {
                                    it.remove(subscriber.type)
                                }
                            }
                        }
                    }
                }
                it.remove(owner)
            }
        }
    }
}

fun subscribe(owner: LifecycleOwner, type: Any, subscriber: (Any) -> Unit) {
    if (owner !is FragmentActivity && owner !is Fragment) {
        throw IllegalArgumentException("owner must be instance of FragmentActivity Or Fragment")
    }
    lifecycleObserver {
        if (subscribersOwner.containsKey(owner).not()) {
            owner.lifecycle.addObserver(lifecycleObserver)
        }
        val messageSubscriber =
            MessageSubscriber(type, subscriber)
        (subscribersOwner[owner] ?: LinkedList<MessageSubscriber>()) {
            add(messageSubscriber).also {
                subscribersOwner[owner] = this
            }
        }
        (subscribers[type] ?: LinkedList<MessageSubscriber>()) {
            add(messageSubscriber).also {
                subscribers[type] = this
            }
        }
    }
}

fun publish(owner: LifecycleOwner, type: Any, message: Any) {
    if (owner !is FragmentActivity && owner !is Fragment) {
        throw IllegalArgumentException("owner must be instance of FragmentActivity Or Fragment")
    }
    lifecycleObserver {
        subscribers[type]?.forEach {
            uiHandler { it.subscriber(message) }
        }
    }
}

inline fun <reified T : Any> FragmentActivity.subscribe(noinline subscriber: (T) -> Unit) {
    @Suppress("UNCHECKED_CAST")
    (subscribe(
        this,
        T::class,
        subscriber as (Any) -> Unit
    ))
}

inline fun <reified T : Any> FragmentActivity.publish(message: T) =
    publish(this, T::class, message)

inline fun <reified T : Any> Fragment.subscribe(noinline subscriber: (T) -> Unit) {
    @Suppress("UNCHECKED_CAST")
    (subscribe(
        this,
        T::class,
        subscriber as (Any) -> Unit
    ))
}

inline fun <reified T : Any> Fragment.publish(message: T) =
    publish(this, T::class, message)
//endregion

//region toast
fun FragmentActivity.toast(text: CharSequence, block: (Toast.() -> Unit)? = null) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).apply {
        if (block != null) {
            block()
        }
        show()
    }
}

fun Fragment.toast(text: String, block: (Toast.() -> Unit)? = null) =
        requireActivity().toast(text, block)
//endregion

//region view's visibility
fun View.visible() = run { visibility = View.VISIBLE }
fun View.invisible() = run { visibility = View.INVISIBLE }
fun View.gone() = run { visibility = View.GONE }
//endregion