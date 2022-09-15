package com.shevy.gifapp.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shevy.gifapp.ListenerSample
import com.shevy.gifapp.data.GiphyDC
import com.shevy.gifapp.databinding.ActivityMainBinding
import com.shevy.gifapp.domain.interactors.Gif
import com.shevy.gifapp.presentation.GifsAdapter
import com.shevy.gifapp.domain.GifsApi
import com.shevy.gifapp.domain.interactors.GifsInteractor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var apiInterface: Call<GiphyDC>
    lateinit var searchEditText: String
    private val interactor = GifsInteractor.create()

    // TODO инициировать адаптер сразу здесть
    private val adapter = ListenerSample(::onClick)
    //adapter.addGifs(gifs)

    private fun onClick(gif: Gif) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        searchEditText = binding.searchEditText.text?.trim().toString()

        if (savedInstanceState != null) {
            searchEditText = savedInstanceState.getString("search").toString()
            Log.d("TestLogs", "SearchEditText = $searchEditText")
        }

        Log.d("TestLogs", "Search text = ${searchEditText.isEmpty()}")

        apiInterface = if (searchEditText.isEmpty()) {
            GifsApi.create().trendingGifs("Wh80AKplXriFbdAoHIjQa6pQgEWuVwLx", 20, "g")
        } else {
            GifsApi.create().searchingGifs(apiKey, searchEditText, limit, offset, rating, lang)
        }

        binding.searchButton.setOnClickListener {
            searchEditText = binding.searchEditText.text?.trim().toString()

            apiInterface = if (searchEditText.isEmpty()) {

                GifsApi.create().trendingGifs("Wh80AKplXriFbdAoHIjQa6pQgEWuVwLx", 20, "g")
            } else {
                GifsApi.create().searchingGifs(apiKey, searchEditText, limit, offset, rating, lang)
            }

            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

            apiEnqueue(apiInterface)
        }

/*        lifecycleScope.launch {
            val gifs = interactor.getTrendingGifs().await()
            // TODO положить гифки в адаптер
        }*/

/*        MainScope().launch {
            GifsInteractor.create().getTrendingGifs().await()
        }*/
        apiEnqueue(apiInterface)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("search", binding.searchEditText.text.toString())
    }

    private fun createFilter(searchText: String): HashMap<String, String> {
        val filter = HashMap<String, String>()
        filter["api_key"] = "Wh80AKplXriFbdAoHIjQa6pQgEWuVwLx"
        filter["q"] = searchText
        filter["limit"] = "20"
        filter["offset"] = "0"
        filter["rating"] = "g"
        filter["lang"] = "en"

        return filter
    }

    private fun apiEnqueue(apiInterface: Call<GiphyDC>) {
        apiInterface.enqueue(object : Callback<GiphyDC> {
            override fun onResponse(call: Call<GiphyDC>, response: Response<GiphyDC>) {

                val listener = object : GifsAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        val intent = Intent(this@MainActivity, SecondActivity::class.java)
                        intent.putExtra(
                            "url",
                            response.body()!!.data[position].images.original.url
                        )
                        startActivity(intent)
                    }
                }
                val recyclerAdapter =
                    response.body()?.let {
                        GifsAdapter(this@MainActivity, it.data, listener)
                    }
                binding.recyclerView.adapter = recyclerAdapter

                Log.d("TestLog", "On Response Success ${response.body()?.data}")
            }

            override fun onFailure(call: Call<GiphyDC>, t: Throwable) {
                Log.d("TestLogs", "On Failure  ${t.message}")
            }
        })
    }

    //delete it
    companion object {
        private const val apiKey = "Wh80AKplXriFbdAoHIjQa6pQgEWuVwLx"
        private const val limit = 20
        private const val offset = "0"
        private const val rating = "g"
        private const val lang = "en"
    }
}