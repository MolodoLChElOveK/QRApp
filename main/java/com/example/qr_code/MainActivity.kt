package com.example.qr_code

import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.create_qr).setOnClickListener {
            val intent = Intent(this, GenActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.scan_qr).setOnClickListener {
            //val intent = Intent(this, ::class.java)
            //startActivity(intent)
        }

        findViewById<Button>(R.id.decod_qr).setOnClickListener {
            val intent = Intent(this, SimpleScanActivity::class.java)
            startActivity(intent)
        }

    }
}