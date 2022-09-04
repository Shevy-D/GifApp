package com.shevy.gifapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.shevy.gifapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

/*        fun fillList(): List<Gifs> {
            val data = mutableListOf<Gifs>()
            (0..30).forEach { i -> data.add(Gifs("$i element from Gifs()", "hey")) }
            return data
        }*/

        val rcView = binding.recyclerView
        val recyclerAdapter = RecyclerViewAdapter(this)
        rcView.layoutManager = LinearLayoutManager(this)
        //rcView.adapter = recyclerAdapter
        rcView.adapter = recyclerAdapter

        val apiInterface = ApiInterface.create().getGifs()

        apiInterface.enqueue(object : Callback<TestDC> {

            override fun onResponse(call: Call<TestDC>, response: Response<TestDC>) {

                Log.d("TestLogs", "On Response Success ${response.body()?.data?.first_name}")
                /*if (response?.body() != null)
                    recyclerAdapter.setGifsListItems(response.body()!!)*/
            }

            override fun onFailure(call: Call<TestDC>, t: Throwable) {
                Log.d("TestLogs", "On Failure  ${t.message}")
            }
        })
    }
}
