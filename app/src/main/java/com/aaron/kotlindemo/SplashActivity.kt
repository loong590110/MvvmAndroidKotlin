package com.aaron.kotlindemo

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.aaron.kotlindemo.base.BaseActivity
import com.aaron.kotlindemo.databinding.ActivitySplashBinding

/**
 * Created by Developer Zailong Shi on 2020-01-07.
 */
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySplashBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_splash
        )
        binding.apply {
            txtTitle.apply {
                text = "splash"
                setTextColor(Color.parseColor("#ffffff"))
            }
            Handler().postDelayed({
                Navigation.from(this@SplashActivity)
                    .to("/main?from=${SplashActivity::class.simpleName}&flag=100")
                    .also {
                        finish()
                    }
            }, 1000)
        }
    }
}