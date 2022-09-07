package com.shevy.gifapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.shevy.gifapp.data.GiphyDC
import com.shevy.gifapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var apiInterface: Call<GiphyDC>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //rcView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        binding.searchButton.setOnClickListener {

            val filter = HashMap<String, String>()
            filter["api_key"] = "Wh80AKplXriFbdAoHIjQa6pQgEWuVwLx"
            filter["q"] = binding.searchEditText.text.toString()
            filter["limit"] = "25"
            filter["offset"] = "0"
            filter["rating"] = "g"
            filter["lang"] = "en"

            apiInterface = ApiInterface.create().getGifsHashMapSearch(filter)
            apiEnqueue(apiInterface)
        }

        apiInterface = ApiInterface.create().getGifs("Wh80AKplXriFbdAoHIjQa6pQgEWuVwLx", 25, "g")
        apiEnqueue(apiInterface)
    }

    private fun apiEnqueue(apiInterface: Call<GiphyDC>) {
        apiInterface.enqueue(object : Callback<GiphyDC> {
            override fun onResponse(call: Call<GiphyDC>, response: Response<GiphyDC>) {

                val recyclerAdapter = response.body()?.let { RecyclerViewAdapter(it.data) }
                binding.recyclerView.adapter = recyclerAdapter

                Log.d("TestLogs", "On Response Success ${response.body()?.data}")
                /*if (response?.body() != null)
                    recyclerAdapter.setGifsListItems(response.body()!!)*/
            }

            override fun onFailure(call: Call<GiphyDC>, t: Throwable) {
                Log.d("TestLogs", "On Failure  ${t.message}")
            }
        })
    }
}
