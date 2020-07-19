package com.julius.mytube.views.subscription

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.julius.mytube.databinding.FragmentSubscriptionBinding
import com.julius.mytube.events.HomeMessage
import com.julius.mytube.extends.inflate
import com.julius.mytube.extends.invoke
import com.julius.mytube.extends.subscribe
import com.julius.mytube.extends.toast
import com.julius.mytube.models.home.VideoInfo
import com.julius.mytube.viewmodels.home.HomeViewModel
import com.julius.mytube.viewmodels.main.TopNavigationViewModel
import com.julius.mytube.views.viewholders.ItemHomeSubscriptionsViewHolder
import com.julius.mytube.views.viewholders.ItemHomeVideoViewHolder

/**
 * Created by Developer Zailong Shi on 2020-01-06.
 */
class SubscriptionFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel>()
    private val topNavigationViewModel by activityViewModels<TopNavigationViewModel>()

    companion object {
        fun newInstance(flag: Int) = SubscriptionFragment()
            .apply {
                Bundle().apply {
                    putInt("flag", flag)
                    arguments = this
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflate<FragmentSubscriptionBinding>(inflater, container).apply {
            onSubscribe()
            lifecycleOwner = this@SubscriptionFragment.viewLifecycleOwner
            ItemHomeSubscriptionsViewHolder(headerView.root as ViewGroup, headerView)
                .onBindViewHolder(0)
            recyclerView {
                layoutManager =
                    LinearLayoutManager(context)
                addItemDecoration(
                    DividerItemDecoration(
                        context,
                        LinearLayoutManager.VERTICAL
                    )
                )
                val items = arrayListOf<VideoInfo?>()
                adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                    override fun onCreateViewHolder(
                        parent: ViewGroup, viewType: Int
                    ): RecyclerView.ViewHolder =
                        when (viewType) {
                            1 -> ItemHomeSubscriptionsViewHolder(parent)
                            else -> ItemHomeVideoViewHolder(parent)
                        }

                    override fun getItemCount() = items.size * 100

                    @SuppressLint("SetTextI18n")
                    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                        when (holder) {
                            is ItemHomeVideoViewHolder ->
                                holder.apply {
                                    items[position % items.size]?.apply {
                                        onBindViewHolder(this, position)
                                    }
                                }
                        }
                    }
                }
                homeViewModel.getHomeListData().observe(this@SubscriptionFragment) {
                    adapter?.apply {
                        items.clear()
                        items.addAll(it?.toList() ?: emptyList())
                        notifyDataSetChanged()
                    }
                } + {
                    toast(it?.message)
                }
                topNavigationViewModel.onSearch(viewLifecycleOwner) { position ->
                    if (position == 0) {
                        homeViewModel.search().observe(viewLifecycleOwner) {
                            adapter?.apply {
                                items.clear()
                                items.addAll(it?.toList() ?: emptyList())
                                notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
        }.root
    }

    private fun onSubscribe() {
        subscribe<HomeMessage> { toast(it.desc) }
    }
}