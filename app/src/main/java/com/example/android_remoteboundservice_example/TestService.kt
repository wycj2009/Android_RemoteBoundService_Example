package com.example.android_remoteboundservice_example

import android.app.Service
import android.content.Intent
import android.os.*
import android.widget.Toast

class TestService : Service() {

    private val testMessenger: Messenger = Messenger(TestHandler())

    override fun onBind(intent: Intent): IBinder {
        return testMessenger.binder
    }

    inner class TestHandler : Handler(Looper.getMainLooper()) {

        override fun handleMessage(msg: Message) {
            val testString: String? = msg.data.getString("TestString")
            Toast.makeText(applicationContext, testString, Toast.LENGTH_SHORT).show()
        }

    }

}