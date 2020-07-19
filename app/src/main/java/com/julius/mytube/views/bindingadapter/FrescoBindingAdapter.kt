package com.julius.mytube.views.bindingadapter

import androidx.databinding.BindingAdapter
import com.facebook.drawee.view.SimpleDraweeView

@BindingAdapter("android:actualImageUri")
fun setActualImageUri(view: SimpleDraweeView, uri: String) {
    view.setImageURI(uri)
}