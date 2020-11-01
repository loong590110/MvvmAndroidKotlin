package com.julius.mytube.extends

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class MutexLiveData<P, N> {
    private val positiveLiveData by lazy { MutableLiveData<P?>() }
    private val negativeLiveData by lazy { MutableLiveData<N?>() }

    fun getPositiveValue() = positiveLiveData.value
    fun setPositiveValue(value: P?) {
        positiveLiveData.value = value
    }

    fun postPositiveValue(value: P?) {
        positiveLiveData.postValue(value)
    }

    fun getNegativeValue() = negativeLiveData.value
    fun setNegativeValue(value: N?) {
        negativeLiveData.value = value
    }

    fun postNegativeValue(value: N?) {
        negativeLiveData.postValue(value)
    }

    fun observe(owner: LifecycleOwner, observer: (P?) -> Unit): NegativeObservable<N?> {
        positiveLiveData.observe(owner, Observer(observer))
        return NegativeObservable(owner, negativeLiveData)
    }

    class NegativeObservable<N>(
        private val owner: LifecycleOwner,
        private val negativeLiveData: MutableLiveData<N?>
    ) {
        fun observe(observer: (N?) -> Unit) {
            negativeLiveData.observe(owner, Observer(observer))
        }

        infix fun and(observer: (N?) -> Unit) = observe(observer)

        operator fun plus(observer: (N?) -> Unit) = and(observer)
    }
}