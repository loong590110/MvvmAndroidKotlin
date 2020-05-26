package com.aaron.kotlindemo

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aaron.kotlindemo.base.BaseFragment
import com.aaron.kotlindemo.base.Scheduler
import com.aaron.kotlindemo.databinding.FragmentHomeBinding
import com.aaron.kotlindemo.event.HomeMessage

/**
 * Created by Developer Zailong Shi on 2020-01-06.
 */
class HomeFragment : BaseFragment() {

    @Autowired(name = "flag")
    private var flag: Int? = null

    companion object {
        fun newInstance(flag: Int): HomeFragment {
            val fragment = HomeFragment()
            val arguments = Bundle()
            arguments.putInt("flag", flag)
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return FragmentHomeBinding.inflate(inflater, container, false).apply {
            recyclerView {
                layoutManager = GridLayoutManager(context, 4)
                adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                    override fun onCreateViewHolder(
                            parent: ViewGroup, viewType: Int
                    ): RecyclerView.ViewHolder {
                        with(parent.context) {
                            val textView = TextView(this)
                            textView {
                                setPadding(0, 15(dp), 0, 15(dp))
                                gravity = Gravity.CENTER
                                setOnClickListener {
                                    uiHandler {
                                        publish("$text clicked")
                                    }
                                }
                            }
                            return object : RecyclerView.ViewHolder(textView) {}
                        }
                    }

                    override fun getItemCount() = 100

                    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                        (holder.itemView as TextView).text = "item $position"
                    }
                }
            }
        }.root
    }

    override fun onSubscribe(scheduler: Scheduler) {
        scheduler.subscribe<String> {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }.subscribe<Int> {
            Toast.makeText(context, "Int $flag = $it", Toast.LENGTH_SHORT).show()
        }.subscribe<HomeMessage> {
            Toast.makeText(context, it.desc, Toast.LENGTH_SHORT).show()
        }
    }
}