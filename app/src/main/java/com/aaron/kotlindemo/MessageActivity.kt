package com.aaron.kotlindemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.aaron.kotlindemo.databinding.ActivityMessageBinding
import com.aaron.kotlindemo.event.HomeMessage
import com.aaron.kotlindemo.event.MainMessage
import com.aaron.kotlindemo.event.Message
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
                    publish(Message(desc = "message"))
                    publish(MainMessage(desc = "main"))
                    publish(HomeMessage(desc = "home"))
                }
            }
        }
    }

    private fun onSubscribe() {
        subscribe<Message> {
            Toast.makeText(this, it.desc, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBack() {
        finish()
    }
}