package com.julius.mytube.views.message

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.julius.mytube.databinding.FragmentHomeBinding
import com.julius.mytube.events.HomeMessage
import com.julius.mytube.extends.*
import com.julius.mytube.models.home.VideoInfo
import com.julius.mytube.viewmodels.home.HomeViewModel
import com.julius.mytube.viewmodels.main.TopNavigationViewModel
import com.julius.mytube.views.viewholders.ItemHomeVideoSmallViewHolder

/**
 * Created by Developer Zailong Shi on 2020-01-06.
 */
class MessageFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel>()
    private val topNavigationViewModel by activityViewModels<TopNavigationViewModel>()

    companion object {
        fun newInstance(flag: Int) = MessageFragment()
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
        return inflate<FragmentHomeBinding>(inflater, container).apply {
            onSubscribe()
            lifecycleOwner = this@MessageFragment.viewLifecycleOwner
            recyclerView {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        outRect.top = 10(dp).also {
                            outRect.bottom = it
                        }
                    }
                })
                val items = arrayListOf<VideoInfo?>()
                adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                    override fun onCreateViewHolder(
                        parent: ViewGroup, viewType: Int
                    ): RecyclerView.ViewHolder {
                        return ItemHomeVideoSmallViewHolder(parent)
                    }

                    override fun getItemCount() = items.size * 100

                    @SuppressLint("SetTextI18n")
                    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                        (holder as ItemHomeVideoSmallViewHolder).apply {
                            items[position % items.size]?.apply {
                                onBindViewHolder(this, position)
                            }
                        }
                    }
                }
                homeViewModel.getHomeListData().observe(this@MessageFragment) {
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