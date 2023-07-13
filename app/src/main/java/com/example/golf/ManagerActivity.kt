package com.example.golf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.golf.databinding.ActivityManagerBinding

class ManagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.exitButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}