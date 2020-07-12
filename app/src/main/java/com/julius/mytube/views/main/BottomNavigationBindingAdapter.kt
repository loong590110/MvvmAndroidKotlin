package com.julius.mytube.views.main

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("selected")
fun onTabSelected(view: View, selected: Boolean) {
    view.isSelected = selected
}