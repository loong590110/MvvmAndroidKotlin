package com.julius.mytube.widgets

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.julius.mytube.databinding.NavigationBarBinding
import com.julius.mytube.extends.inflate
import com.julius.mytube.extends.invoke

/**
 * Created by Developer Zailong Shi on 2020-01-14.
 */
class NavigationBar @JvmOverloads constructor(
        context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val binding = inflate<NavigationBarBinding>()

    init {
        if (!isInEditMode) {
            if (context is Activity) {
                binding {
                    btnBack.setOnClickListener {
                        context.finish()
                    }
                }
            }
        }
    }
}