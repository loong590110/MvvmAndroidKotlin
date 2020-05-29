package com.julius.mytube.views.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.julius.mytube.R
import com.julius.mytube.databinding.ActivitySplashBinding
import com.julius.mytube.extends.runOnUiThread
import com.julius.mytube.extends.setContentView
import com.julius.mytube.routers.Navigation

/**
 * Created by Developer Zailong Shi on 2020-01-07.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivitySplashBinding>(R.layout.activity_splash).apply {
            runOnUiThread(2000) {
                Navigation.from(this@SplashActivity)
                        .to("/main?from=${SplashActivity::class.simpleName}&flag=100")
                        .also {
                            finish()
                        }
            }
        }
    }
}