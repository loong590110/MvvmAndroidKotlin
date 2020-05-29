package com.julius.mytube.views.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.julius.mytube.R
import com.julius.mytube.views.home.HomeFragment
import com.julius.mytube.databinding.ActivityMainBinding
import com.julius.mytube.events.MainMessage
import com.julius.mytube.events.Message
import com.julius.mytube.extends.*
import com.julius.mytube.injects.Autowired
import com.julius.mytube.routers.Navigation
import com.julius.mytube.utils.clickCountDetector

class MainActivity : AppCompatActivity() {

    @Autowired
    private var from: String? = null

    @Autowired
    private var flag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSubscribe()
        val binding = setContentView<ActivityMainBinding>(R.layout.activity_main)
        binding {
            txtTitle {
                text = "Main Activity from $from@$flag"
                setOnClickListener {
                    publish(Message(desc = "from main"))
                }
            }
            btnSearch.setOnClickListener {
                //publish("hello, home fragment")
                //publish(10000)
                Navigation.from(this@MainActivity).to("/message")
            }
            tabLayout {
                tabMode = TabLayout.MODE_SCROLLABLE
                setupWithViewPager(viewPager)
            }
            viewPager.adapter = object : FragmentPagerAdapter(
                supportFragmentManager,
                BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
            ) {
                override fun getItem(position: Int) =
                    HomeFragment.newInstance(position)

                override fun getCount() = 10

                override fun getPageTitle(position: Int): CharSequence? {
                    return arrayListOf("home", "discovery", "message", "mine")[position % 4]
                }
            }
        }
    }

    private fun onSubscribe() {
        subscribe<MainMessage> { toast(it.desc) }
    }

    override fun onBackPressed() {
        clickCountDetector(1500) {
            when (it) {
                1 -> toast("click again to exit")
                2 -> super.onBackPressed()
            }
        }
    }
}
