package com.shevy.gifapp.presentation.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shevy.gifapp.databinding.ActivityMainBinding
import com.shevy.gifapp.domain.interactors.Gif
import com.shevy.gifapp.domain.interactors.GifInteractor
import com.shevy.gifapp.presentation.detail.DetailActivity
import com.shevy.gifapp.presentation.favorite.FavoriteActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var searchEditText: String
    lateinit var searchView: SearchView

    // Запрашиваем зависимость через Koin (это позволяет нам сделать функция by inject()
    private val interactor: GifInteractor by inject()
    //private val interactor = GifsInteractorImpl.create()

    private val mainViewModel by viewModel<MainViewModel>()

    private val adapter = GifsAdapter(::onClick)

    private fun onClick(gif: Gif) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("url", gif.url)
        intent.putExtra("previewUrl", gif.previewUrl)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchView = binding.searchView
        val recyclerView = binding.recyclerView
        val favoritesButton = binding.favoritesButton

/*        lifecycleScope.launchWhenStarted {
            mainViewModel.searchText.collect { searchEditText = it }
        }*/

        lifecycleScope.launchWhenStarted {
            mainViewModel.gifs.collect { adapter.setGifs(it) }
        }

        lifecycleScope.launchWhenStarted {
            mainViewModel.loading.collect { loading -> binding.progressBar.isVisible = loading }
        }

        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = adapter

        searchByLetters()

        lifecycleScope.launch {
            val gifs = getApiResponse()
            adapter.setGifs(gifs)
        }

        favoritesButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
        }
    }

    private fun searchByLetters() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    lifecycleScope.launch {
                        val gifs = getApiResponse()
                        adapter.setGifs(gifs)
                    }
                }
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    lifecycleScope.launch {
                        val gifs = getApiResponse()
                        adapter.setGifs(gifs)
                    }
                } else {
                    lifecycleScope.launch {
                        val gifs = interactor.getSearchingGifs(newText).await()
                        adapter.setGifs(gifs)
                    }
                }
                return false
            }
        })
    }

    private suspend fun getApiResponse(): List<Gif> {
        return if (mainViewModel.searchText.value.isNullOrEmpty()) {
            interactor.getTrendingGifs().await()
        } else {
            interactor.getSearchingGifs(mainViewModel.searchText.value.toString()).await()
        }
    }
}