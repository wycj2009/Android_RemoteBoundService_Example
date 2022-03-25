package com.example.android_remoteboundservice_example

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import com.example.android_remoteboundservice_example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var testMessenger: Messenger? = null

    private val testConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            testMessenger = Messenger(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            testMessenger = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindService(
                Intent(applicationContext, TestService::class.java),
                testConnection,
                Context.BIND_AUTO_CREATE
        )

        binding.button.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        testMessenger ?: return

        val msg = Message.obtain().apply {
            data = Bundle().apply {
                putString("TestString", "Message Received!")
            }
        }

        try {
            testMessenger?.send(msg)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

}