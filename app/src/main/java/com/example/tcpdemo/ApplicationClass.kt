package com.example.tcpdemo

import android.app.Application

class ApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()

        TCPManager.setDataListener(object : DataReceivedListener {
            override fun onDataReceived(data: String) {
                // Handle received data here
            }
        })

        TCPManager.startTcpCommunication()
    }
}