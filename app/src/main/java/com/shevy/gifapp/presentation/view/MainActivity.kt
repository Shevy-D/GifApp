package com.shevy.gifapp.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
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

    private lateinit var vm: MainViewModel

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

        vm = ViewModelProvider(this)[MainViewModel::class.java]

        val searchButton = binding.searchButton
        val recyclerView = binding.recyclerView

        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = adapter

        //binding.searchEditText.text = vm.resultLiveMutable.value
        checkSearchEditText(savedInstanceState)

/*        vm.resultLive.observe(this) { text ->
            binding.searchEditText.text = text
        }*/

        searchButton.setOnClickListener {
            searchEditText = binding.searchEditText.text?.trim().toString()

            lifecycleScope.launch {
                val gifs = interactor.getSearchingGifs(searchEditText).await()
                adapter.setGifs(gifs)
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

    private fun checkSearchEditText(savedInstanceState: Bundle?) {
        searchEditText = binding.searchEditText.text?.trim().toString()
        if (savedInstanceState != null) {
            searchEditText = savedInstanceState.getString("search").toString()
        }
    }

    private suspend fun getApiResponse(): List<Gif> {
        return if (searchEditText.isEmpty()) {
            interactor.getTrendingGifs().await()
        } else {
            interactor.getSearchingGifs(searchEditText).await()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("search", binding.searchEditText.text.toString())
    }
}