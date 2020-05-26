package com.aaron.kotlindemo

import android.app.Activity
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import androidx.fragment.app.Fragment

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
typealias dp2px = (Float) -> Int

operator fun Float.invoke(dp: dp2px) = dp(this)
operator fun Int.invoke(dp: dp2px) = dp(toFloat())
val Activity.dp get() = dp2px(resources)
val Fragment.dp get() = dp2px(resources)
fun dp2px(resources: Resources?) = { dp: Float ->
    resources?.run {
        TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics
        ).toInt()
    } ?: 0
}
//endregion