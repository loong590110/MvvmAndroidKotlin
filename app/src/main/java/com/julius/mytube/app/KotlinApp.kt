package com.julius.mytube.app

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco
import com.julius.mytube.routers.Navigation
import com.julius.mytube.routers.RouteTable

/**
 * Created by Developer Zailong Shi on 2020-01-06.
 */
class KotlinApp : MultiDexApplication() {

    companion object {
        lateinit var context: Context
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        context = this
    }

    override fun onCreate() {
        super.onCreate()
        Navigation.init(RouteTable())
        Fresco.initialize(this)
        registerActivityLifecycleCallbacks(AutowaredActivityLifecycleCallbacks())
    }
}