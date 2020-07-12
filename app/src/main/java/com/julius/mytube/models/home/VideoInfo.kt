package com.julius.mytube.models.home

/**
 * Created by Developer Zailong Shi on 2020/5/29.
 */
data class VideoInfo(
    val title: String? = null,
    val coverImageUrl: String? = null,
    val avatarImageUrl: String? = null,
    val username: String? = null,
    val watchTimes: Long = 0,
    val uploadTime: Long = 0
)