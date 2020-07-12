package com.julius.mytube.viewmodels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julius.mytube.exception.InvalidException
import com.julius.mytube.extends.MutexLiveData
import com.julius.mytube.injects.DaggerHomeComponent
import com.julius.mytube.injects.HomeModule
import com.julius.mytube.models.home.HomeModel
import com.julius.mytube.models.home.VideoInfo
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Developer Zailong Shi on 2020/5/29.
 */
class HomeViewModel : ViewModel() {

    private var homeModel: HomeModel? = null

    @Inject
    fun setHomeModel(homeModel: HomeModel) {
        this.homeModel = homeModel
    }

    init {
        DaggerHomeComponent.builder().homeModule(HomeModule()).build().inject(this)
    }

    fun getHomeListData() = MutexLiveData<List<VideoInfo?>?, InvalidException?>()
        .apply {
            viewModelScope.launch {
                val data = coroutineScope {
                    homeModel?.let {
                        arrayListOf<VideoInfo?>(
                            VideoInfo(
                                title = "骁话一下：为什么欧洲没有互联网？",
                                coverImageUrl = "https://i0.hdslb.com/bfs/archive/b97ffe9e3728ff8586917c3ddd60632219d1b587.jpg@353w_220h.webp",
                                avatarImageUrl = "https://i1.hdslb.com/bfs/face/1d6f100b921c9b48914eda8f3b37e76ba9ef4ca5.jpg@68w_68h.webp",
                                username = "观察者网",
                                watchTimes = 363 * 10000,
                                uploadTime = SimpleDateFormat(
                                    "yyyy-MM-dd HH:mm:ss",
                                    Locale.CHINA
                                ).parse("2020-06-24 21:23:54")?.time
                                    ?: 0
                            )
                        ).apply { it.videoInfo = this }
                    }
                }
                postPositiveValue(data)
            }
        }

    fun search() = MutexLiveData<List<VideoInfo?>?, InvalidException?>()
        .apply {
            viewModelScope.launch {
                val data = coroutineScope {
                    homeModel?.let {
                        arrayListOf<VideoInfo?>(
                            VideoInfo(
                                title = "骁话一下：为什么欧洲没有互联网？",
                                coverImageUrl = "https://i0.hdslb.com/bfs/archive/b97ffe9e3728ff8586917c3ddd60632219d1b587.jpg@353w_220h.webp",
                                avatarImageUrl = "https://i1.hdslb.com/bfs/face/1d6f100b921c9b48914eda8f3b37e76ba9ef4ca5.jpg@68w_68h.webp",
                                username = "观察者网",
                                watchTimes = 363 * 10000,
                                uploadTime = SimpleDateFormat(
                                    "yyyy-MM-dd HH:mm:ss",
                                    Locale.CHINA
                                ).parse("2020-06-24 21:23:54")?.time
                                    ?: 0
                            )
                        ).apply { it.videoInfo = this }
                    }
                }
                postPositiveValue(data)
            }
        }
}