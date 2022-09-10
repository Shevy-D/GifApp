package com.shevy.gifapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.shevy.gifapp.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailImageView = binding.detailImageView

        val url = intent.getStringExtra("url")
        Log.d("TestSecond", "Get url $url")
        Glide.with(this).load(url).into(detailImageView)
    }
}