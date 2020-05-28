package com.aaron.kotlindemo.extends

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

/**
 * build.gradle:
 * android{
 *      ...
 *      dataBinding {
 *          enabled true
 *      }
 * }
 *
 * Created by Developer Zailong Shi on 2020/5/28.
 */
inline fun <reified T : ViewDataBinding> ViewGroup.inflate(attach: Boolean = true): T {
    return T::class.java.getDeclaredMethod(
            "inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
    ).invoke(
            null, LayoutInflater.from(context), this, attach
    ) as T
}