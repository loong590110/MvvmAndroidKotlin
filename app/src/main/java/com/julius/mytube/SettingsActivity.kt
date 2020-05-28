package com.julius.mytube

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.julius.mytube.event.Message
import com.julius.mytube.extends.publish
import com.julius.mytube.extends.subscribe
import com.julius.mytube.extends.toast

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