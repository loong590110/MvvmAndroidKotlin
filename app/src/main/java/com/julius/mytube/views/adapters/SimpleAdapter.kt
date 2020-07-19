package com.julius.mytube.views.adapters

import androidx.recyclerview.widget.RecyclerView

abstract class SimpleAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected val items = ArrayList<T?>()

    fun setItems(items: List<T?>?) {
        this.items.clear()
        items?.let {
            this.items.addAll(it)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size
}