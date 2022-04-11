package com.julius.mytube.app

import androidx.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco
import com.julius.mytube.routers.Navigator
import com.julius.mytube.routers.RouteTable

/**
 * Created by Developer Zailong Shi on 2020-01-06.
 */
class KotlinApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Navigator.init(RouteTable())
        Fresco.initialize(this)
        registerActivityLifecycleCallbacks(AutowaredActivityLifecycleCallbacks())
    }
}