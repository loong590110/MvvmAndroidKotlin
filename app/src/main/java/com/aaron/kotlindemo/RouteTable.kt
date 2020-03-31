package com.aaron.kotlindemo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils

/**
 * Created by Developer Zailong Shi on 2020-01-07.
 */
class RouteTable : Navigation.Route {

    private val routeTable = mapOf(
        "/splash" to SplashActivity::class.java,
        "/main" to MainActivity::class.java,
        "/message" to MessageActivity::class.java
    )

    override fun targetIntent(context: Context, uri: Uri?): Intent? {
        with(routeTable.entries.find { TextUtils.equals(it.key, uri?.path) }?.value) {
            return if (this == null) null else Intent(context, this).apply {
                uri?.encodedQuery?.split('&')?.forEach {
                    it.split('=').takeIf({ size == 2 }) {
                        putExtra(Uri.decode(this[0]), Uri.decode(this[1]))
                    }
                }
            }
        }
    }
}