package com.julius.mytube.app

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.julius.mytube.injects.AutowiredHelper
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
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacksAdapter() {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                AutowiredHelper.inject(activity, activity.intent)
                if (activity is FragmentActivity) {
                    activity.supportFragmentManager.fragments.forEach { it ->
                        fun inject(fragment: Fragment) {
                            AutowiredHelper.inject(fragment, fragment.arguments)
                            fragment.childFragmentManager.fragments.forEach {
                                inject(it)
                            }
                        }
                        inject(it)
                    }
                }
            }
        })
    }
}