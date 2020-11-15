package com.julius.mytube.views.bindingadapter

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.julius.mytube.extends.dp
import com.julius.mytube.extends.invoke

@BindingAdapter("android:selected")
fun setSelected(view: View, selected: Boolean) {
    view.isSelected = selected
}

@BindingAdapter(
    value = ["drawableLeft", "drawableWidth", "drawableHeight"],
    requireAll = true
)
fun setDrawableLeft(view: TextView, drawable: Drawable, width: Float = 0f, height: Float = 0f) {
    view.apply {
        compoundDrawables.let {
            if (width > 0 && height > 0) {
                drawable.setBounds(0, 0, width(dp), height(dp))
            }
            setCompoundDrawables(drawable, it[1], it[2], it[3])
        }
    }
}