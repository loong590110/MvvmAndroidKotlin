package com.julius.mytube.viewmodels.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class BottomNavigationViewModel : ViewModel() {
    private val selectedPosition by lazy { MutableLiveData<Int>() }

    fun onTabSelected(owner: LifecycleOwner, observer: (Int) -> Unit) {
        selectedPosition.observe(owner, Observer(observer))
    }

    fun setTabSelected(position: Int) = selectedPosition.postValue(position)
}