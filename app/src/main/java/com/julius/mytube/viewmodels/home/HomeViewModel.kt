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
                                title = "【这就是中国】学不了中国防疫，这次轮到西方国家讲“国情”了",
                                coverImageUrl = "https://i1.hdslb.com/bfs/archive/8e875f25c6beb5af663a52a69d5ae37114b77edd.jpg@353w_220h.webp",
                                avatarImageUrl = "https://i0.hdslb.com/bfs/face/9fde235378806dacaa4761bf32a6498a7531b55a.jpg@68w_68h.webp",
                                username = "观视频工作室",
                                watchTimes = 294 * 1000,
                                uploadTime = SimpleDateFormat(
                                    "yyyy-MM-dd HH:mm:ss",
                                    Locale.CHINA
                                ).parse("2020-03-26 15:55:07")?.time
                                    ?: 0
                            ),
                            VideoInfo(
                                title = "【范神论】中国：统治者的责任意识很重要！特朗普：责任是什么？",
                                coverImageUrl = "https://i0.hdslb.com/bfs/archive/445df2ae67d2c36bbe072029e74f01b2943af782.jpg@336w_190h.webp",
                                avatarImageUrl = "https://i0.hdslb.com/bfs/face/9fde235378806dacaa4761bf32a6498a7531b55a.jpg@68w_68h.webp",
                                username = "观视频工作室",
                                watchTimes = 282 * 1000,
                                uploadTime = SimpleDateFormat(
                                    "yyyy-MM-dd HH:mm:ss",
                                    Locale.CHINA
                                ).parse("2020-07-03 18:54:03")?.time
                                    ?: 0
                            ),
                            VideoInfo(
                                title = "【眉山论剑】美国变成科技强国的根本原因，中国学到了多少？",
                                coverImageUrl = "https://i2.hdslb.com/bfs/archive/92d570d9be45ab9915db54d79e6d9016fece7123.jpg@336w_190h.webp",
                                avatarImageUrl = "https://i1.hdslb.com/bfs/face/1d6f100b921c9b48914eda8f3b37e76ba9ef4ca5.jpg@68w_68h.webp",
                                username = "观视频工作室",
                                watchTimes = 603 * 1000,
                                uploadTime = SimpleDateFormat(
                                    "yyyy-MM-dd HH:mm:ss",
                                    Locale.CHINA
                                ).parse("2020-07-01 00:09:32")?.time
                                    ?: 0
                            ),
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

    fun search() = getHomeListData()
}