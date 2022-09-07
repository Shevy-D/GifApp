package com.shevy.gifapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.shevy.gifapp.data.GiphyDC
import com.shevy.gifapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rcView = binding.recyclerView
        //rcView.layoutManager = LinearLayoutManager(this)
        rcView.layoutManager = GridLayoutManager(this, 2)

        //val apiInterface = ApiInterface.create().getGifs("Wh80AKplXriFbdAoHIjQa6pQgEWuVwLx", 25, "g")

        val filter = HashMap<String, String>()
        filter["api_key"] = "Wh80AKplXriFbdAoHIjQa6pQgEWuVwLx"
        filter["q"] = "taxi" //change it
        filter["limit"] = "25"
        filter["offset"] = "0"
        filter["rating"] = "g"
        filter["lang"] = "en"

        val apiInterface = ApiInterface.create().getGifsHashMapSearch(filter)

        apiInterface.enqueue(object : Callback<GiphyDC> {
            override fun onResponse(call: Call<GiphyDC>, response: Response<GiphyDC>) {

                val recyclerAdapter = response.body()?.let { RecyclerViewAdapter(it.data) }
                rcView.adapter = recyclerAdapter

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
