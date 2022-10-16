package com.shevy.gifapp.presentation.favorite

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.shevy.gifapp.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val testButton = binding.testButton

        testButton.setOnClickListener {
            Toast.makeText(this, "Test toast", Toast.LENGTH_SHORT).show()
        }
    }
}