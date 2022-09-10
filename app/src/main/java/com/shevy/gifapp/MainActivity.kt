package com.shevy.gifapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shevy.gifapp.data.GiphyDC
import com.shevy.gifapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var apiInterface: Call<GiphyDC>
    lateinit var searchEditText: String

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
            ApiInterface.create().getGifs("Wh80AKplXriFbdAoHIjQa6pQgEWuVwLx", 30, "g")
        } else {
            val filter = HashMap<String, String>()
            filter["api_key"] = "Wh80AKplXriFbdAoHIjQa6pQgEWuVwLx"
            filter["q"] = searchEditText
            filter["limit"] = "30"
            filter["offset"] = "0"
            filter["rating"] = "g"
            filter["lang"] = "en"

            ApiInterface.create().getGifsHashMapSearch(filter)
        }

        binding.searchButton.setOnClickListener {
            val filter = HashMap<String, String>()
            filter["api_key"] = "Wh80AKplXriFbdAoHIjQa6pQgEWuVwLx"
            filter["q"] = binding.searchEditText.text?.trim().toString()
            filter["limit"] = "30"
            filter["offset"] = "0"
            filter["rating"] = "g"
            filter["lang"] = "en"

            searchEditText = binding.searchEditText.text?.trim().toString()

            apiInterface = if (searchEditText.isEmpty()) {
                ApiInterface.create().getGifs("Wh80AKplXriFbdAoHIjQa6pQgEWuVwLx", 30, "g")
            } else {
                ApiInterface.create().getGifsHashMapSearch(filter)
            }

            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

            Log.d("TestLogs", "ApiInterface SetOnClick $apiInterface}")
            apiEnqueue(apiInterface)
        }

        Log.d("TestLogs", "ApiInterface $apiInterface}")

        apiEnqueue(apiInterface)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("search", binding.searchEditText.text.toString())
    }

    private fun apiEnqueue(apiInterface: Call<GiphyDC>) {
        apiInterface.enqueue(object : Callback<GiphyDC> {
            override fun onResponse(call: Call<GiphyDC>, response: Response<GiphyDC>) {

                val listener = object : RecyclerViewAdapter.OnItemClickListener {
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
                        RecyclerViewAdapter(this@MainActivity, it.data, listener)
                    }
                binding.recyclerView.adapter = recyclerAdapter

                Log.d("TestLog", "On Response Success ${response.body()?.data}")
            }

            override fun onFailure(call: Call<GiphyDC>, t: Throwable) {
                Log.d("TestLogs", "On Failure  ${t.message}")
            }
        })
    }
}
