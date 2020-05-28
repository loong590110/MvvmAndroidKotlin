package com.julius.mytube.utils

/**
 * Created by Developer Zailong Shi on 2020-01-13.
 */
private var firstClickTime = 0L
private var clickCount = 0

fun clickCountDetector(duration: Long, callback: (Int) -> Unit) {
    val timeOut = System.currentTimeMillis() - firstClickTime >= duration
    if (timeOut) {
        clickCount = 0
    }
    if (++clickCount == 1) {
        firstClickTime = System.currentTimeMillis()
    }
    callback(clickCount)
}