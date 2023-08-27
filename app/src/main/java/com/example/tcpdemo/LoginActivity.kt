package com.example.tcpdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.tcpdemo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), DataReceivedListener {
    lateinit var binding:ActivityLoginBinding
    private var isCommunicationStarted = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_login)
        TCPManager.setDataListener(this)

        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            finish()
        }
    }
    override fun onResume() {
        super.onResume()
       /* // Start TCP communication only if it hasn't been started already
        if (!isCommunicationStarted) {
            TCPManager.startTcpCommunication()
            isCommunicationStarted = true
        }*/
    }
    override fun onPause() {
        super.onPause()

        // Remove the data listener when the activity is paused
        TCPManager.removeDataListener()
    }
    override fun onDestroy() {
        super.onDestroy()

        TCPManager.removeDataListener()
       // TCPManager.stopTcpCommunication()
    }
    override fun onDataReceived(data: String) {
        if(data!=null)
        {
            binding.loginText.setText(data)
        }
    }
}