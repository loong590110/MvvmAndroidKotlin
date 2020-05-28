package com.julius.mytube

import android.app.Application
import android.content.Context

/**
 * Created by Developer Zailong Shi on 2020-01-06.
 */
class KotlinApp : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        Navigation.init(RouteTable())
    }
}