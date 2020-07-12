package com.julius.mytube.views.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.julius.mytube.databinding.FragmentMainBinding
import com.julius.mytube.extends.inflate
import com.julius.mytube.extends.invoke
import com.julius.mytube.viewmodels.main.BottomNavigationViewModel
import com.julius.mytube.viewmodels.main.TopNavigationViewModel
import com.julius.mytube.views.home.HomeFragment

class MainFragment : Fragment() {

    private val bottomNavigationViewModel by viewModels<BottomNavigationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflate<FragmentMainBinding>(inflater, container).apply {
            lifecycleOwner = viewLifecycleOwner
            btnSearch.setOnClickListener {
                MainFragmentDirections.actionMainFragmentToSearchFragment(viewPager.currentItem)
                    .apply {
                        findNavController().navigate(this)
                    }
            }
            bottomNavigationViewModel = this@MainFragment.bottomNavigationViewModel
                .apply {
                    onTabSelected(this@MainFragment) { viewPager.currentItem = it }
                }
            viewPager {
                addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                    override fun onPageSelected(position: Int) {
                        setPosition(position)
                    }
                })
                adapter = object : FragmentPagerAdapter(
                    childFragmentManager,
                    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
                ) {
                    override fun getItem(position: Int): Fragment =
                        when (position) {
                            in 0..4 -> HomeFragment.newInstance(position)
                            else -> Fragment()
                        }

                    override fun getCount() = 5

                    override fun getPageTitle(position: Int): CharSequence? {
                        return arrayOf("home", "discovery", "subscribe", "message", "mine")
                            .let {
                                it[position % it.size]
                            }
                    }
                }
            }
        }.root
    }
}