package com.aaron.kotlindemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aaron.kotlindemo.event.Message
import com.aaron.kotlindemo.extends.publish
import com.aaron.kotlindemo.extends.subscribe
import com.aaron.kotlindemo.extends.toast

/**
 * Created by Developer Zailong Shi on 2020-01-14.
 */
class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSubscribe()
        publish(Message("SETTINGS"))
    }

    private fun onSubscribe() {
        subscribe<Message> { toast(it.desc) }
    }
}