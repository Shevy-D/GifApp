package com.shevy.gifapp.presentation.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var searchEditText: String

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

        val searchButton = binding.searchButton
        val recyclerView = binding.recyclerView
        val favoritesButton = binding.favoritesButton

        searchEditText = binding.searchEditText.text?.trim().toString()

        mainViewModel.searchText.observe(this, Observer {
            searchEditText = it
        })

        mainViewModel.gifs.observe(this, Observer {
            adapter.setGifs(it)
        })

        mainViewModel.loading.observe(this, Observer { loading ->
            binding.progressBar.isVisible = loading
        })

        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = adapter

        searchButton.setOnClickListener {

            mainViewModel.onSearchTextChanged(binding.searchEditText.text.toString())

/*            if (searchEditText.isEmpty()) {
                Toast.makeText(this, "Enter text", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    val gifs = interactor.getSearchingGifs(searchEditText).await()
                    adapter.setGifs(gifs)
                }
            }
*/

/*            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
*/
        }

        lifecycleScope.launch {
            val gifs = getApiResponse()
            adapter.setGifs(gifs)
        }

        favoritesButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
        }
    }

    private suspend fun getApiResponse(): List<Gif> {
        return if (mainViewModel.searchText.value.isNullOrEmpty()) {
            interactor.getTrendingGifs().await()
        } else {
            interactor.getSearchingGifs(mainViewModel.searchText.value.toString()).await()
        }
    }
}