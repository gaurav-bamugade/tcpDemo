package com.example.tcpdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.tcpdemo.databinding.ActivityPage3Binding

class Page3Activity : AppCompatActivity() , DataReceivedListener{
    lateinit var binding:ActivityPage3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_page3)
        TCPManager.setDataListener(this)
        binding.page3Btn.setOnClickListener {
            startActivity(Intent(this@Page3Activity,MainActivity::class.java))
            finish()
        }
    }

    override fun onDataReceived(data: String) {
        if(data!=null)
        {
            binding.page3Text.setText(data)
        }
    }
    override fun onPause() {
        super.onPause()
       // TCPManager.removeDataListener()
        TCPManager.stopTcpCommunication()
    }
    override fun onDestroy() {
        super.onDestroy()
        TCPManager.removeDataListener()
    }
}