package com.aaron.kotlindemo

import android.graphics.Color
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import com.aaron.kotlindemo.base.BaseActivity
import com.aaron.kotlindemo.base.invoke
import com.aaron.kotlindemo.base.uiHandler
import com.aaron.kotlindemo.databinding.ActivitySplashBinding

/**
 * Created by Developer Zailong Shi on 2020-01-07.
 */
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivitySplashBinding>(
            this, R.layout.activity_splash
        ).apply {
            txtTitle {
                text = "splash"
                setTextColor(Color.parseColor("#ffffff"))
            }
            uiHandler(2000) {
                Navigation.from(this@SplashActivity)
                    .to("/main?from=${SplashActivity::class.simpleName}&flag=100")
                    .also {
                        finish()
                    }
            }
        }
    }
}