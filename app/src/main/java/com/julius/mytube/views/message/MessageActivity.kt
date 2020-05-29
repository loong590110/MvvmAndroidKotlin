package com.julius.mytube.views.message

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.julius.mytube.R
import com.julius.mytube.databinding.ActivityMessageBinding
import com.julius.mytube.events.HomeMessage
import com.julius.mytube.events.MainMessage
import com.julius.mytube.events.Message
import com.julius.mytube.extends.*

/**
 * Created by Developer Zailong Shi on 2020-01-13.
 */
class MessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSubscribe()
        setContentView<ActivityMessageBinding>(R.layout.activity_message).apply {
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
}