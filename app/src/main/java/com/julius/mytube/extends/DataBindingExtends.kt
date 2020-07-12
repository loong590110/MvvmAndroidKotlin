package com.julius.mytube.extends

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

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
inline fun <reified T : ViewDataBinding> ViewGroup.inflate(
    inflater: LayoutInflater? = null, attach: Boolean = true
): T =
    T::class.java.getDeclaredMethod(
        "inflate",
        LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
    ).invoke(
        null, inflater ?: LayoutInflater.from(context), this, attach
    ) as T

inline fun <reified T : ViewDataBinding> Fragment.inflate(
    inflater: LayoutInflater? = null, container: ViewGroup?
): T =
    run { container!!.inflate(inflater, false) }

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified T : ViewDataBinding> Activity.setContentView(
    @LayoutRes layoutResId: Int
): T =
    DataBindingUtil.setContentView(this, layoutResId)