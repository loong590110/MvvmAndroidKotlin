package com.julius.mytube.views.home

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.julius.mytube.databinding.FragmentHomeBinding
import com.julius.mytube.events.HomeMessage
import com.julius.mytube.extends.*
import com.julius.mytube.injects.Autowired
import com.julius.mytube.viewmodels.home.HomeViewModel

/**
 * Created by Developer Zailong Shi on 2020-01-06.
 */
class HomeFragment : Fragment() {

    @Autowired(name = "flag")
    private var flag: Int? = null
    private val homeViewModel: HomeViewModel? = null

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
        return inflate<FragmentHomeBinding>(inflater, container).apply {
            onSubscribe()
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
                                    runOnUiThread {
                                        publish("$text clicked")
                                    }
                                }
                                visible()
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

    private fun onSubscribe() {
        subscribe<String> { toast(it) }
        subscribe<Int> { toast("Int $flag = $it") }
        subscribe<HomeMessage> { toast(it.desc) }
    }
}