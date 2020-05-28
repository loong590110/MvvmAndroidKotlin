package com.julius.mytube

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.julius.mytube.databinding.ActivitySplashBinding
import com.julius.mytube.extends.invoke
import com.julius.mytube.extends.runOnUiThread
import com.julius.mytube.extends.setContentView

/**
 * Created by Developer Zailong Shi on 2020-01-07.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivitySplashBinding>(R.layout.activity_splash).apply {
            txtTitle {
                text = "splash"
                setTextColor(Color.parseColor("#ffffff"))
            }
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