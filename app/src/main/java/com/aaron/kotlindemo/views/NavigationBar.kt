package com.aaron.kotlindemo.views

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.aaron.kotlindemo.databinding.NavigationBarBinding
import com.aaron.kotlindemo.extends.inflate

/**
 * Created by Developer Zailong Shi on 2020-01-14.
 */
class NavigationBar @JvmOverloads constructor(
        context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: NavigationBarBinding = inflate()

    init {
        if (!isInEditMode) {
            if (context !is OnBackListener) {
                throw IllegalStateException("Context must is instance of OnBackListener.")
            }
            binding.apply {
                btnBack.setOnClickListener {
                    context.onBack()
                }
            }
        }
    }

    interface OnBackListener {
        fun onBack()
    }
}