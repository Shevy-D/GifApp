package com.shevy.gifapp

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shevy.gifapp.data.Data
import com.shevy.gifapp.databinding.RecyclerviewItemBinding
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(private val context: Context, private val gifs: List<Data>) :
    RecyclerView.Adapter<RecyclerViewAdapter.GifsViewHolder>() {

    class GifsViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = RecyclerviewItemBinding.bind(item)

        val imageView = binding.imageView
        //val textView = binding.textView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifsViewHolder {
        return GifsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GifsViewHolder, position: Int) {
        val itemsGifs = gifs[position]
        //holder.textView.text = itemsGifs.title
        //Picasso.get().load(itemsGifs.images.original.url).into(holder.imageView)
        Glide.with(context).load(itemsGifs.images.original.url).into(holder.imageView)
    }

    override fun getItemCount(): Int = gifs.size
}