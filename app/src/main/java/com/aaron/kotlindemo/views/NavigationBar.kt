package com.aaron.kotlindemo.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.aaron.kotlindemo.R
import com.aaron.kotlindemo.databinding.NavigationBarBinding
import java.lang.IllegalStateException

/**
 * Created by Developer Zailong Shi on 2020-01-14.
 */
class NavigationBar(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?) : this(context, null)

    val binding: NavigationBarBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context), R.layout.navigation_bar, this, true
    )

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