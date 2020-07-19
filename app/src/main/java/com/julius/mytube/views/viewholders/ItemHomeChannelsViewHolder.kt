package com.julius.mytube.views.viewholders

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.julius.mytube.R
import com.julius.mytube.databinding.ItemHomeChannelsBinding
import com.julius.mytube.extends.inflate

class ItemHomeChannelsViewHolder(
    parent: ViewGroup,
    val binding: ItemHomeChannelsBinding = parent.inflate(attach = false)
) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun onBindViewHolder(position: Int) {
        binding.apply {
            arrayOf(
                mapOf(
                    "icon" to R.drawable.ic_baseline_whatshot_24,
                    "text" to "时下流行",
                    "tint" to 0xfff42222.toInt()
                ),
                mapOf(
                    "icon" to R.drawable.ic_baseline_whatshot_24,
                    "text" to "音乐",
                    "tint" to 0xff99f888.toInt()
                ),
                mapOf(
                    "icon" to R.drawable.ic_baseline_whatshot_24,
                    "text" to "游戏",
                    "tint" to 0xff8888f9.toInt()
                ),
                mapOf(
                    "icon" to R.drawable.ic_baseline_whatshot_24,
                    "text" to "新闻",
                    "tint" to 0xfff84444.toInt()
                ),
                mapOf(
                    "icon" to R.drawable.ic_baseline_whatshot_24,
                    "text" to "电影和节目",
                    "tint" to 0xfff92853.toInt()
                ),
                mapOf(
                    "icon" to R.drawable.ic_baseline_whatshot_24,
                    "text" to "时尚和美容",
                    "tint" to 0xff987997.toInt()
                )
            ).let {
                arrayOf(channel1, channel2, channel3, channel4, channel5, channel6)
                    .apply {
                        for (i in this.indices) {
                            this[i].apply {
                                it[i].run {
                                    icon = this["icon"] as Int
                                    text = this["text"] as String
                                    root.background.setColorFilter(
                                        this["tint"] as Int,
                                        PorterDuff.Mode.MULTIPLY
                                    )
                                }
                            }
                        }
                    }
            }
        }
    }
}

@BindingAdapter("channelIcon")
fun setChannelIcon(view: TextView, resId: Int) {
    val left = view.resources.getDrawable(resId).apply {
        setBounds(0, 0, this.intrinsicWidth, this.intrinsicHeight)
    }
    view.setCompoundDrawables(left, null, null, null)
}