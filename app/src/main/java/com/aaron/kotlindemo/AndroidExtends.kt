package com.aaron.kotlindemo

import android.app.Activity
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import java.util.*
import kotlin.collections.LinkedHashMap

/**
 * Created by Developer Zailong Shi on 2020-01-07.
 */
operator fun Handler.invoke(block: () -> Unit) = post(block)
operator fun Handler.invoke(delay: Long, block: () -> Unit) = postDelayed(block, delay)
inline operator fun <T> T?.invoke(block: T.() -> Unit) = this?.apply(block)

//region ui handler
val Activity.uiHandler by lazy {
    Handler(Looper.getMainLooper())
}
val Fragment.uiHandler get() = Activity::uiHandler.get(requireActivity())
//endregion

//region dp to px
private typealias dp2px = (Float) -> Int

operator fun Float.invoke(dp: dp2px) = dp(this)
operator fun Int.invoke(dp: dp2px) = dp(toFloat())
val Activity.dp get() = dp2px(resources)
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
        val uiHandler = Handler(Looper.getMainLooper())

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onUnsubscribe(owner: LifecycleOwner) {
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
    owner {
        lifecycleObserver {
            if (subscribersOwner.containsKey(owner).not()) {
                lifecycle.addObserver(lifecycleObserver)
            }
            val messageSubscriber = MessageSubscriber(type, subscriber)
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
}

fun publish(type: Any, message: Any) {
    lifecycleObserver {
        subscribers[type]?.forEach {
            uiHandler { it.subscriber(message) }
        }
    }
}

inline fun <reified T : Any> FragmentActivity.subscribe(noinline subscriber: (T) -> Unit) {
    @Suppress("UNCHECKED_CAST")
    subscribe(this, T::class, subscriber as (Any) -> Unit)
}

inline fun <reified T : Any> FragmentActivity.publish(message: T) = publish(T::class, message)

inline fun <reified T : Any> Fragment.subscribe(noinline subscriber: (T) -> Unit) {
    @Suppress("UNCHECKED_CAST")
    subscribe(this, T::class, subscriber as (Any) -> Unit)
}

inline fun <reified T : Any> Fragment.publish(message: T) = publish(T::class, message)
//endregion