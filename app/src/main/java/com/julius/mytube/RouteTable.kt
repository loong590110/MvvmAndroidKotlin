package com.julius.mytube

/**
 * Created by Developer Zailong Shi on 2020-01-07.
 */
class RouteTable : Navigation.Route(
        mapOf(
                "/splash" to SplashActivity::class.java,
                "/main" to MainActivity::class.java,
                "/message" to MessageActivity::class.java,
                "/settings" to SettingsActivity::class.java
        )
)