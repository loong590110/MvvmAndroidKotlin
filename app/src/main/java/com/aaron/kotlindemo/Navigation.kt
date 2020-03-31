package com.aaron.kotlindemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Created by Developer Zailong Shi on 2020-01-07.
 */
class Navigation private constructor(private val context: Context) {

    companion object {
        private lateinit var route: Route

        fun init(route: Route) {
            this.route = route
        }

        fun from(context: Context) = Navigation(context)
    }

    fun to(path: String, requestCode: Int = -1) {
        route.targetIntent(context, Uri.parse(path))?.apply {
            if (context is Activity) {
                context.startActivityForResult(this, requestCode)
                val finish: Boolean? = getStringExtra("finish")?.toBoolean()
                if (finish == true) context.finish()
            }
        }
    }

    interface Route {
        fun targetIntent(context: Context, uri: Uri?): Intent?
    }
}