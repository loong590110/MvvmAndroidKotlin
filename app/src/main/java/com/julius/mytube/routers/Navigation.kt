package com.julius.mytube.routers

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
            Companion.route = route
        }

        fun from(context: Context) = Navigation(context)
    }

    @JvmOverloads
    fun to(path: String, requestCode: Int = -1) {
        route.targetIntent(context, Uri.parse(path))?.apply {
            if (context is Activity) {
                context.startActivityForResult(this, requestCode)
                val finish = getStringExtra("finish")
                if (finish == "true") context.finish()
            }
        }
    }

    open class Route(private val routeTable: Map<String, Class<*>>) {
        fun targetIntent(context: Context, uri: Uri) = routeTable[uri.path]?.let { clazz ->
            Intent(context, clazz).apply {
                uri.encodedQuery?.split('&')?.forEach { field ->
                    field.split('=').takeIf { it.size == 2 }?.let {
                        putExtra(it[0], Uri.decode(it[1]))
                    }
                }
            }
        }
    }
}