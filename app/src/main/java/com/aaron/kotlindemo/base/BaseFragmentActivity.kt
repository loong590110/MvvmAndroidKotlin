package com.aaron.kotlindemo.base

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aaron.kotlindemo.AutowiredHelper
import com.aaron.kotlindemo.takeIf
import com.aaron.kotlindemo.views.NavigationBar
import java.lang.reflect.ParameterizedType

/**
 * Created by Developer Zailong Shi on 2020-01-07.
 */
abstract class BaseActivity : AppCompatActivity(), Scheduler, NavigationBar.OnBackListener {

    final override var subscribersSelf: MutableMap<Class<*>, ArrayList<Any>>? = null
        set(value) {
            if (value != null && field == null) {
                field = value
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AutowiredHelper.inject(this, intent)
        onSubscribe(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        AutowiredHelper.inject(this, intent)
        onSubscribe(this)
    }

    override fun onBack() {
        onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        unsubscribeSelf()
    }

    final override fun <T> subscribe(subscriber: (any: T) -> Unit): Scheduler {
        return super.subscribe(subscriber)
    }

    final override fun <T> unsubscribe(key: T, subscriber: (any: T) -> Unit): Scheduler {
        return super.unsubscribe(key, subscriber)
    }

    final override fun unsubscribeSelf() {
        super.unsubscribeSelf()
    }

    final override fun <T> publish(any: T) {
        super.publish(any)
    }

    open fun onSubscribe(scheduler: Scheduler) {}
}

abstract class BaseFragment : Fragment(), Scheduler {

    final override var subscribersSelf: MutableMap<Class<*>, ArrayList<Any>>? = null
        set(value) {
            if (value != null && field == null) {
                field = value
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AutowiredHelper.inject(this, arguments)
        onSubscribe(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        unsubscribeSelf()
    }

    final override fun <T> subscribe(subscriber: (any: T) -> Unit): Scheduler {
        return super.subscribe(subscriber)
    }

    final override fun <T> unsubscribe(key: T, subscriber: (any: T) -> Unit): Scheduler {
        return super.unsubscribe(key, subscriber)
    }

    final override fun unsubscribeSelf() {
        super.unsubscribeSelf()
    }

    final override fun <T> publish(any: T) {
        super.publish(any)
    }

    open fun onSubscribe(scheduler: Scheduler) {}
}

interface Scheduler {

    var subscribersSelf: MutableMap<Class<*>, ArrayList<Any>>?

    companion object {
        private val subscribers = mutableMapOf<Class<*>, ArrayList<Any>>()
    }

    fun <T> subscribe(subscriber: (any: T) -> Unit): Scheduler {
        subscriber::class.java.genericInterfaces.takeIf({ isNotEmpty() }) {
            val type = this[0]
            if (type is ParameterizedType) {
                type.actualTypeArguments.takeIf({ isNotEmpty() }) {
                    val key = this[0]
                    if (key is Class<*>) {
                        var subs = subscribers[key]
                        if (subs == null) {
                            subs = arrayListOf(subscriber)
                            subscribers[key] = subs
                        } else {
                            subs.add(subscriber)
                        }
                        if (subscribersSelf == null) {
                            subscribersSelf = mutableMapOf()
                        }
                        var subsSelf = subscribersSelf?.get(key)
                        if (subsSelf == null) {
                            subsSelf = arrayListOf(subscriber)
                            subscribersSelf?.put(key, subsSelf)
                        } else {
                            subsSelf.add(subscriber)
                        }
                    }
                }
            }
        }
        return this
    }

    fun <T> unsubscribe(key: T, subscriber: (any: T) -> Unit): Scheduler {
        subscribers.filterKeys { it === key }.forEach { (_, value) ->
            value.remove(subscriber)
        }
        return this
    }

    fun <T> unsubscribe(key: T, subscribers: ArrayList<(any: T) -> Unit>): Scheduler {
        Scheduler.subscribers.filterKeys { it === key }.forEach { (_, value) ->
            value.removeAll(subscribers)
        }
        return this
    }

    fun unsubscribeSelf() {
        subscribersSelf?.forEach { (key, value) ->
            @Suppress("UNCHECKED_CAST")
            unsubscribe(key, value as ArrayList<(any: Class<*>) -> Unit>)
        }
    }

    fun <T> publish(any: T) {
        subscribers.filter { (key, _) -> key.isInstance(any) }
                .forEach { (_, value) ->
                    value.forEach {
                        @Suppress("UNCHECKED_CAST")
                        (it as (any: T) -> Unit)(any)
                    }
                }
    }
}

private lateinit var handler: Handler
val BaseActivity.uiHandler: Handler
    get() {
        handler = Handler(Looper.getMainLooper())
        return handler
    }

operator fun Handler.invoke(block: () -> Unit) {
    post(block)
}

operator fun Handler.invoke(delay: Long, block: () -> Unit) {
    postDelayed(block, delay)
}

operator fun <T> T.invoke(block: T.() -> Unit) {
    block()
}