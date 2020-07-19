package com.julius.mytube.views.mine

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.julius.mytube.extends.dp
import com.julius.mytube.extends.invoke
import com.julius.mytube.models.home.VideoInfo
import com.julius.mytube.views.adapters.SimpleAdapter
import com.julius.mytube.views.viewholders.ItemHomeVideoMiddleViewHolder

@BindingAdapter("recentWatchHistories")
fun setRecentWatchHistories(view: RecyclerView, histories: List<VideoInfo?>?) {
    view.adapter ?: object : SimpleAdapter<VideoInfo>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ItemHomeVideoMiddleViewHolder(parent)

        override fun getItemCount() = 5 * super.getItemCount()

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder) {
                is ItemHomeVideoMiddleViewHolder ->
                    holder.apply {
                        items[position % items.size]?.apply {
                            onBindViewHolder(this, position)
                        }
                    }
            }
        }
    }.also { adapter ->
        view.apply {
            hasFixedSize()
            setItemViewCacheSize(3)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.right = 10(dp).also { _ ->
                        parent.getChildAdapterPosition(view).takeIf { it == 0 }?.let {
                            outRect.left = 10(dp)
                        }
                    }
                }
            })
        }.adapter = adapter
    }
    @Suppress("UNCHECKED_CAST")
    (view.adapter as SimpleAdapter<VideoInfo?>).setItems(histories)
}