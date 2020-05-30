package com.julius.mytube.app

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.julius.mytube.injects.AutowiredHelper

class AutowaredActivityLifecycleCallbacks : ActivityLifecycleCallbacksAdapter() {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        AutowiredHelper.inject(activity as FragmentActivity)
    }
}