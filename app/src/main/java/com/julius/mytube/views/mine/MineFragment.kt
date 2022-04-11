package com.julius.mytube.views.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.julius.mytube.databinding.FragmentMineBinding
import com.julius.mytube.extends.inflate
import com.julius.mytube.viewmodels.home.HomeViewModel

class MineFragment : Fragment() {
    private val homeViewModel by viewModels<HomeViewModel>()

    companion object {
        fun newInstance(flag: Int) = MineFragment()
            .apply {
                Bundle().apply {
                    putInt("flag", flag)
                    arguments = this
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflate<FragmentMineBinding>(inflater, container).apply {
            lifecycleOwner = viewLifecycleOwner
            homeViewModel.getHomeListData().observe(this@MineFragment) {
                recentWatchHistories = it
            }
        }.root
    }
}