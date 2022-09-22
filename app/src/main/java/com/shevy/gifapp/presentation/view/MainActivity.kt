package com.shevy.gifapp.presentation.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shevy.gifapp.data.Gif
import com.shevy.gifapp.data.GifsInteractorImpl
import com.shevy.gifapp.databinding.ActivityMainBinding
import com.shevy.gifapp.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var searchEditText: String
    private val interactor = GifsInteractorImpl.create()

    private lateinit var viewModel: MainViewModel

    // TODO инициировать адаптер сразу здесть
    //private val adapter = ListenerSample(::onClick)
    private val adapter = GifsAdapter(::onClick)
    //adapter.addGifs(gifs)

    private fun onClick(gif: Gif) {
        val intent = Intent(this@MainActivity, SecondActivity::class.java)
        intent.putExtra("url", gif.url)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchButton = binding.searchButton
        val recyclerView = binding.recyclerView

        searchEditText = binding.searchEditText.text?.trim().toString()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.searchText.observe(this, Observer {
            searchEditText = it
        })

        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = adapter

        searchButton.setOnClickListener {
            searchEditText = binding.searchEditText.text?.trim().toString()
            viewModel.searchText.value = searchEditText

            if (searchEditText.isEmpty()) {
                Toast.makeText(this, "Enter text", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    val gifs = interactor.getSearchingGifs(searchEditText).await()
                    adapter.setGifs(gifs)
                }
            }

            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }

        lifecycleScope.launch {
            val gifs = getApiResponse()
            // TODO положить гифки в адаптер
            adapter.setGifs(gifs)
        }
    }

    private suspend fun getApiResponse(): List<Gif> {
        return if (viewModel.searchText.value.isNullOrEmpty()) {
            interactor.getTrendingGifs().await()
        } else {
            interactor.getSearchingGifs(viewModel.searchText.value.toString()).await()
        }
    }
}