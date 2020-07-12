package com.julius.mytube.routers

import com.julius.mytube.views.main.MainActivity
import com.julius.mytube.views.splash.SplashActivity

/**
 * Created by Developer Zailong Shi on 2020-01-07.
 */
class RouteTable : Navigation.Route(
    mapOf(
        "/splash" to SplashActivity::class.java,
        "/main" to MainActivity::class.java
    )
)