package com.aaron.kotlindemo.utils

import android.os.Handler

/**
 * Created by Developer Zailong Shi on 2020-01-13.
 */
private var clickCount = 0

fun doubleClickExitTimer(delayMills: Long, delayHandler: () -> Unit): Int {
    when (++clickCount) {
        1 -> Handler().postDelayed({ clickCount = 0 }, delayMills)
        2 -> {
            delayHandler(); clickCount = 0
        }
    }
    return clickCount
}