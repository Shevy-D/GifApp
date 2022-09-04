package com.shevy.gifapp

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shevy.gifapp.databinding.RecyclerviewItemBinding

//change it
class RecyclerViewAdapter(val context: Context /*private val gifs: List<Gifs>*/) :
    RecyclerView.Adapter<RecyclerViewAdapter.GifsViewHolder>() {

    var gifsList : List<TestDC> = listOf()

    class GifsViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = RecyclerviewItemBinding.bind(item)

        val imageView = binding.imageView
        val textView = binding.textView
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setGifsListItems(gifsList: List<TestDC>){
        this.gifsList = gifsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifsViewHolder {
        return GifsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GifsViewHolder, position: Int) {
        //change it
        //holder.imageView.setImageResource(gifs[position].length)
        holder.textView.text = gifsList[position].data.email
        //holder.textView.text = gifsList[position].imageUrl

    }

    override fun getItemCount(): Int = gifsList.size /*gifsList.size*/
}