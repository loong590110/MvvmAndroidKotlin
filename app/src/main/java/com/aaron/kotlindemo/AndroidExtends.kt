package com.aaron.kotlindemo

import com.aaron.kotlindemo.utils.DensityUtils

/**
 * Created by Developer Zailong Shi on 2020-01-07.
 */
inline val Float.dp: Int
    get() = DensityUtils.dp2px(KotlinApp.context, this)

inline val Int.dp: Int
    get() = toFloat().dp

inline fun <T> T.takeIf(predicate: T.() -> Boolean, then: T.() -> Unit): T {
    if (predicate()) then()
    return this
}