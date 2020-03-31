package com.aaron.kotlindemo

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.aaron.kotlindemo.base.BaseActivity
import com.aaron.kotlindemo.base.Scheduler
import com.aaron.kotlindemo.databinding.ActivityMessageBinding
import com.aaron.kotlindemo.event.HomeMessage
import com.aaron.kotlindemo.event.MainMessage
import com.aaron.kotlindemo.event.Message

/**
 * Created by Developer Zailong Shi on 2020-01-13.
 */
class MessageActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMessageBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_message
        )
        binding.apply {
            navigationBar.apply {
                this.binding.apply {
                    txtTitle.text = "Message"
                }
            }
            txtContent.apply {
                text = "Message Activity"
                setOnClickListener {
                    publish(Message(desc = "message"))
                    publish(MainMessage(desc = "main"))
                    publish(HomeMessage(desc = "home"))
                }
            }
        }
    }

    override fun onSubscribe(scheduler: Scheduler) {
        scheduler.subscribe<Message> {
            Toast.makeText(this, it.desc, Toast.LENGTH_SHORT).show()
        }
        scheduler.subscribersSelf = null
    }
}