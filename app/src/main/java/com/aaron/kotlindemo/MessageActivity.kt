package com.aaron.kotlindemo

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.aaron.kotlindemo.databinding.ActivityMessageBinding
import com.aaron.kotlindemo.event.HomeMessage
import com.aaron.kotlindemo.event.MainMessage
import com.aaron.kotlindemo.event.Message
import com.aaron.kotlindemo.extends.publish
import com.aaron.kotlindemo.extends.subscribe
import com.aaron.kotlindemo.extends.toast
import com.aaron.kotlindemo.views.NavigationBar

/**
 * Created by Developer Zailong Shi on 2020-01-13.
 */
class MessageActivity : AppCompatActivity(), NavigationBar.OnBackListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSubscribe()
        setContentView<ActivityMessageBinding>(
                this, R.layout.activity_message
        ).apply {
            navigationBar {
                binding {
                    txtTitle.text = "Message"
                }
            }
            txtContent {
                text = "Message Activity"
                setOnClickListener {
                    publish(Message(desc = "MESSAGE"))
                    publish(MainMessage(desc = "MAIN"))
                    publish(HomeMessage(desc = "HOME"))
                }
            }
        }
    }

    private fun onSubscribe() {
        subscribe<Message> {
            toast(it.desc) {
                setGravity(Gravity.CENTER, 0, 0)
                duration = Toast.LENGTH_LONG
            }
        }
    }

    override fun onBack() {
        finish()
    }
}