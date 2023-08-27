package com.example.tcpdemo

import kotlinx.coroutines.*
import java.io.IOException
import java.net.ServerSocket
import java.text.SimpleDateFormat
import java.util.*

class TCP(private val listener:DataReceivedListener?) {

    // Configuration
    private val timeToRun: Long = 150 * 1000
    private var runningJob: Job = Job()

    // Start TCP communication
    fun start() {
        val portNumber = 9000

        GlobalScope.launch {
            val serverSocket = ServerSocket(portNumber)
            println("Server is listening on port $portNumber")

            val clientSocket = serverSocket.accept()

            println("Client connected: ${clientSocket.inetAddress.hostAddress}")
            println("Will disconnect after ${timeToRun / 1000} seconds...")

            runningJob = launch {
                delay(timeToRun)
                println("Disconnecting Client after ${timeToRun / 1000} seconds...")
                runningJob.cancel()
            }

            try {
                while (!runningJob.isCancelled) {
                    val data = ByteArray(1000)
                    val len: Int = clientSocket.inputStream.read(data)
                    if (len > 0) {
                        val receivedData = bytesToHexString(data, len)
                        val timestamp: String = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date())
                        val tagRead = receivedData.substring(16, receivedData.length)
                        listener?.onDataReceived(tagRead)
                        println("$timestamp - ${clientSocket.remoteSocketAddress} - $tagRead")
                    }
                }
                clientSocket.close()
                if (clientSocket.isClosed) {
                    println("Client Disconnected")
                }
            } catch (e: IOException) {
                println("Error: ${e.message}")
            } finally {
                runningJob.cancel()
            }
        }
    }

    private fun bytesToHexString(data: ByteArray, len: Int): String {
        val stringBuilder = StringBuilder()
        for (i in 0 until len) {
            stringBuilder.append(String.format("%02X", data[i]))
        }
        return stringBuilder.toString()
    }
}