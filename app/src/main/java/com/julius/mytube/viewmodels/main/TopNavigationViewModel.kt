package com.julius.mytube.viewmodels.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class TopNavigationViewModel : ViewModel() {
    private val searchLiveData by lazy { MutableLiveData<Int>() }

    fun onSearch(owner: LifecycleOwner, observer: (Int) -> Unit) {
        searchLiveData.observe(owner, Observer(observer))
    }

    fun search(position: Int) = searchLiveData.postValue(position)
}