package com.julius.mytube.views.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.julius.mytube.R
import com.julius.mytube.databinding.ActivityMainBinding
import com.julius.mytube.events.MainMessage
import com.julius.mytube.extends.setContentView
import com.julius.mytube.extends.subscribe
import com.julius.mytube.extends.toast
import com.julius.mytube.injects.Autowired
import com.julius.mytube.utils.clickCountDetector

class MainActivity : AppCompatActivity() {

    @Autowired
    private var from: String? = null

    @Autowired
    private var flag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSubscribe()
        setContentView<ActivityMainBinding>()
    }

    private fun onSubscribe() {
        subscribe<MainMessage> { toast(it.desc) }
        subscribe<String> { toast(it) }
    }

    override fun onBackPressed() {
        findNavController(R.id.nav_host_fragment_container).apply {
            if (navigateUp()) {
                return
            }
        }
        clickCountDetector(1500) {
            when (it) {
                1 -> toast("click again to exit")
                2 -> super.onBackPressed()
            }
        }
    }
}
