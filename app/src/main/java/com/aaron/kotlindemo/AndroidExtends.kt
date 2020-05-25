package com.aaron.kotlindemo

import android.app.Activity
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import com.aaron.kotlindemo.utils.DensityUtils

/**
 * Created by Developer Zailong Shi on 2020-01-07.
 */
inline val Float.dp: Int get() = DensityUtils.dp2px(KotlinApp.context, this)
inline val Int.dp: Int get() = toFloat().dp
inline fun <T> T.takeIf(predicate: T.() -> Boolean, then: T.() -> Unit): T {
    if (predicate()) then()
    return this
}

operator fun Handler.invoke(block: () -> Unit) = post(block)
operator fun Handler.invoke(delay: Long, block: () -> Unit) = postDelayed(block, delay)
inline operator fun <T> T?.invoke(block: T.() -> Unit) = this?.apply(block)
val Activity.uiHandler by lazy {
    Handler(Looper.getMainLooper())
}
val Fragment.uiHandler by lazy {
    Handler(Looper.getMainLooper())
}