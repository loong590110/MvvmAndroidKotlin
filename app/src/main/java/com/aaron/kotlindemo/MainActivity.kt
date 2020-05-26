package com.aaron.kotlindemo

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.aaron.kotlindemo.base.BaseActivity
import com.aaron.kotlindemo.base.Scheduler
import com.aaron.kotlindemo.databinding.ActivityMainBinding
import com.aaron.kotlindemo.event.MainMessage
import com.aaron.kotlindemo.event.Message
import com.aaron.kotlindemo.utils.clickCountDetector
import com.google.android.material.tabs.TabLayout

class MainActivity : BaseActivity() {

    @Autowired
    private var from: String? = null

    @Autowired
    private var flag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_main
        )
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
                override fun getItem(position: Int): Fragment {
                    return HomeFragment.newInstance(position)
                }

                override fun getCount(): Int {
                    return 10
                }

                override fun getPageTitle(position: Int): CharSequence? {
                    return arrayListOf("home", "discovery", "message", "mine")[position % 4]
                }
            }
        }
    }

    override fun onSubscribe(scheduler: Scheduler) {
        scheduler.subscribe<MainMessage> {
            Toast.makeText(this, it.desc, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        clickCountDetector(1500) {
            when (it) {
                1 -> Toast.makeText(this, "Click Again To Exit", Toast.LENGTH_SHORT)
                        .show()
                2 -> super.onBackPressed()
            }
        }
    }
}
