package com.julius.mytube.views.viewholders

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.julius.mytube.databinding.ItemHomeSubscriptionsBinding
import com.julius.mytube.databinding.ItemSubscriptionBinding
import com.julius.mytube.databinding.ItemSubscriptionFilterBinding
import com.julius.mytube.extends.inflate
import com.julius.mytube.models.home.Subscription

class ItemHomeSubscriptionsViewHolder(
    parent: ViewGroup,
    val binding: ItemHomeSubscriptionsBinding = parent.inflate(attach = false)
) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun onBindViewHolder(position: Int) {
        binding.apply {
            arrayListOf(
                Subscription(
                    title = "观视频工作室",
                    imageUrl = "https://i0.hdslb.com/bfs/face/9fde235378806dacaa4761bf32a6498a7531b55a.jpg@68w_68h.webp"
                ),
                Subscription(
                    title = "观察者网",
                    imageUrl = "https://i1.hdslb.com/bfs/face/1d6f100b921c9b48914eda8f3b37e76ba9ef4ca5.jpg@68w_68h.webp"
                ),
                Subscription(
                    title = "观视频工作室",
                    imageUrl = "https://i0.hdslb.com/bfs/face/9fde235378806dacaa4761bf32a6498a7531b55a.jpg@68w_68h.webp"
                ),
                Subscription(
                    title = "观察者网",
                    imageUrl = "https://i1.hdslb.com/bfs/face/1d6f100b921c9b48914eda8f3b37e76ba9ef4ca5.jpg@68w_68h.webp"
                )
            ).let {
                subscriptions = it
            }
            arrayListOf("全部", "今天", "继续观看", "未观看内容", "直播", "帖子")
                .let { subscriptionFilters = it }
        }
    }
}

@BindingAdapter("subscriptions")
fun setSubscriptions(view: RecyclerView, subscriptions: List<Subscription?>?) {
    view.adapter ?: object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ItemSubscriptionViewHolder(parent)
        }

        override fun getItemCount(): Int {
            return 50 * (subscriptions?.size ?: 0)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder) {
                is ItemSubscriptionViewHolder ->
                    holder.binding.apply {
                        subscriptions?.get(position % subscriptions.size)?.apply {
                            subscription = this
                        }
                    }
            }
        }

        inner class ItemSubscriptionViewHolder(
            parent: ViewGroup,
            val binding: ItemSubscriptionBinding = parent.inflate(
                attach = false
            )
        ) : RecyclerView.ViewHolder(binding.root)
    }.also {
        view.apply {
            hasFixedSize()
            setItemViewCacheSize(5)
        }.adapter = it
    }
    view.adapter?.notifyDataSetChanged()
}

@BindingAdapter("subscriptionFilters")
fun setSubscriptionFilters(view: RecyclerView, filters: List<String?>?) {
    view.adapter ?: object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ItemDateFilterViewHolder(parent)
        }

        override fun getItemCount(): Int {
            return (filters?.size ?: 0)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder) {
                is ItemDateFilterViewHolder ->
                    holder.binding.apply {
                        filters?.get(position % filters.size)?.apply {
                            filterTitle = this
                            selected = 0 == position
                        }
                    }
            }
        }

        inner class ItemDateFilterViewHolder(
            parent: ViewGroup,
            val binding: ItemSubscriptionFilterBinding = parent.inflate(
                attach = false
            )
        ) : RecyclerView.ViewHolder(binding.root)
    }.also {
        view.apply {
            hasFixedSize()
            setItemViewCacheSize(5)
        }.adapter = it
    }
    view.adapter?.notifyDataSetChanged()
}