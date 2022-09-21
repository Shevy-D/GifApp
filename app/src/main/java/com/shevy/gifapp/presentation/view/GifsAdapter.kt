package com.shevy.gifapp.presentation.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shevy.gifapp.R
import com.shevy.gifapp.data.Gif
import com.shevy.gifapp.databinding.RecyclerviewItemBinding

class GifsAdapter(
    private val onGifSelected: ((gif: Gif) -> Unit)
) :
    RecyclerView.Adapter<GifsAdapter.GifsViewHolder>() {

    private val gif = mutableListOf<Gif>()

    @SuppressLint("NotifyDataSetChanged")
    fun setGifs(gifs: List<Gif>) {
        this.gif.apply {
            clear()
            addAll(gifs)
        }
        notifyDataSetChanged()
        //notifyItemChanged()
    }

    inner class GifsViewHolder(item: View, private val onGifSelected: (gif: Gif) -> Unit) :
        RecyclerView.ViewHolder(item) {
        private val binding = RecyclerviewItemBinding.bind(item)
        val imageView = binding.imageView

        init {
            itemView.setOnClickListener { onGifSelected(gif[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifsViewHolder {
        return GifsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false),
            onGifSelected
        )
    }

    override fun onBindViewHolder(holder: GifsViewHolder, position: Int) {
        val itemsGifs = gif[position]
        Glide.with(holder.imageView.context).load(itemsGifs.previewUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int = gif.size
}