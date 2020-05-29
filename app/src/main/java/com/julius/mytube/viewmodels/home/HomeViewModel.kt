package com.julius.mytube.viewmodels.home

import androidx.lifecycle.ViewModel
import com.julius.mytube.models.home.HomeModel
import javax.inject.Inject

/**
 * Created by Developer Zailong Shi on 2020/5/29.
 */
class HomeViewModel : ViewModel() {
    @Inject
    private var homeModel: HomeModel? = null
}