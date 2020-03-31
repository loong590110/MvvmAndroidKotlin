package com.aaron.kotlindemo.utils

import android.content.Context
import android.util.TypedValue

/**
 * Created by Developer Zailong Shi on 2020-01-06.
 */
object DensityUtils {
    fun dp2px(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}