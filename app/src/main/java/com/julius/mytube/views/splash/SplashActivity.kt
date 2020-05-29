package com.julius.mytube.views.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.julius.mytube.extends.runOnUiThread
import com.julius.mytube.routers.Navigation

/**
 * Created by Developer Zailong Shi on 2020-01-07.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runOnUiThread(2000) {
            Navigation.from(this).to(
                    "/main?from=${javaClass.simpleName}&flag=100"
            ).also {
                finish()
            }
        }
    }
}