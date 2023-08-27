package com.example.tcpdemo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

object TCPManager {
    private var dataListener: DataReceivedListener? = null
    private var communicationJob: Job? = null

    fun setDataListener(listener: DataReceivedListener) {
        dataListener = listener
    }

    fun removeDataListener() {
        dataListener = null
    }

    fun startTcpCommunication() {
        val listener = dataListener
        if (listener != null) {
            val tcpInstance = TCP(listener)
            communicationJob = CoroutineScope(Dispatchers.IO).launch {
                tcpInstance.start()
            }
        }
    }

    fun stopTcpCommunication() {
        communicationJob?.cancel()

        // Perform cleanup, close sockets, etc.
        // ...

        communicationJob = null
    }
}