package com.shevy.gifapp.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shevy.gifapp.R
import com.shevy.gifapp.databinding.ActivityMainBinding
import com.shevy.gifapp.domain.interactors.Gif
import com.shevy.gifapp.domain.interactors.GifInteractor
import com.shevy.gifapp.presentation.detail.DetailActivity
import com.shevy.gifapp.presentation.favorite.FavoriteActivity
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    //lateinit var favoritesButton: Button
    lateinit var searchView: SearchView
    lateinit var recyclerView: RecyclerView

    // Запрашиваем зависимость через Koin (это позволяет нам сделать функция by inject()
    private val interactor: GifInteractor by inject()

    //private val mainViewModel by viewModel<MainViewModel>()

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
        //favoritesButton = binding.favoritesButton

        initRecyclerView()
        searchBySearchView()

        lifecycleScope.launch {
            val gifs = getApiResponse(null)
            adapter.setGifs(gifs)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorites_menu -> this.startActivity(
                Intent(
                    this@MainActivity,
                    FavoriteActivity::class.java
                )
            )
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = adapter
    }

/*    private fun favoritesButtonClick() {
        favoritesButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
        }
    }*/

    private fun searchBySearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                lifecycleScope.launch {
                    val gifs = getApiResponse(query)
                    adapter.setGifs(gifs)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                lifecycleScope.launch {
                    val gifs = getApiResponse(newText)
                    adapter.setGifs(gifs)
                }
                //searchView.clearFocus()
                return false
            }
        })
    }

    private suspend fun getApiResponse(text: String?): List<Gif> {
        return if (text.isNullOrEmpty()) {
            interactor.getTrendingGifs().await()
        } else {
            interactor.getSearchingGifs(text).await()
        }
    }
}