package com.aaron.kotlindemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aaron.kotlindemo.event.Message

/**
 * Created by Developer Zailong Shi on 2020-01-14.
 */
class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSubscribe()
        publish(Message("settings"))
    }

    private fun onSubscribe() {
        subscribe<Message> {
            Toast.makeText(this, it.desc, Toast.LENGTH_SHORT).show()
        }
    }
}