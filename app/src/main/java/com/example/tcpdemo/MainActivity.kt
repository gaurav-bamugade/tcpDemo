package com.example.tcpdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.tcpdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , DataReceivedListener{
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        TCPManager.setDataListener(this)
        binding.mainBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity,MainActivity::class.java))
            finish()
        }
    }

    override fun onDataReceived(data: String) {
        if(data!=null)
        {
            binding.mainText.setText(data)
        }
    }
    override fun onPause() {
        super.onPause()
        //TCPManager.stopTcpCommunication()
        // Remove the data listener when the activity is paused
        TCPManager.removeDataListener()
    }

    override fun onDestroy() {
        super.onDestroy()

        TCPManager.removeDataListener()

        // ... other code ...
    }
}