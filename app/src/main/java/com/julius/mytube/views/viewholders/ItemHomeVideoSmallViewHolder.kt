package com.julius.mytube.views.viewholders

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.julius.mytube.databinding.ItemHomeVideoSmallBinding
import com.julius.mytube.extends.inflate
import com.julius.mytube.models.home.VideoInfo
import java.text.SimpleDateFormat
import java.util.*

class ItemHomeVideoSmallViewHolder(
    parent: ViewGroup,
    val binding: ItemHomeVideoSmallBinding = parent.inflate(attach = false)
) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun onBindViewHolder(item: VideoInfo?, position: Int) {
        binding.apply {
            item?.apply {
                imgCover.setImageURI(coverImageUrl)
                imgAvatar.setImageURI(avatarImageUrl)
                txtTitle.text = title
                txtSummary.text = "$username · ${when {
                    watchTimes > 10000 -> "${watchTimes / 10000}万"
                    else -> watchTimes.toString()
                }}次观看 · ${when (val t = System.currentTimeMillis() - uploadTime) {
                    in 0 until 60 * 1000 -> "刚刚"
                    in 60 * 1000 until 60 * 60 * 1000 -> "${t / 60 * 1000}分钟前"
                    in 60 * 60 * 1000 until 24 * 60 * 60 * 1000 -> "${t / 60 * 60 * 1000}小时前"
                    else -> SimpleDateFormat.getDateInstance()
                        .format(Date(uploadTime))
                }}"
            }
        }
    }
}