package com.shevy.gifapp.presentation.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.shevy.gifapp.R
import com.shevy.gifapp.databinding.RecyclerviewItemBinding
import com.shevy.gifapp.domain.interactors.Gif
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation

class GifsAdapter(
    private val onGifSelected: ((gif: Gif) -> Unit)
) :
    RecyclerView.Adapter<GifsAdapter.GifsViewHolder>() {

    private val gifs = mutableListOf<Gif>()

    @SuppressLint("NotifyDataSetChanged")
    fun setGifs(newGifs: List<Gif>) {
        gifs.apply {
            clear()
            addAll(newGifs)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifsViewHolder {
        return GifsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false),
            onGifSelected
        )
    }

    override fun onBindViewHolder(holder: GifsViewHolder, position: Int) {
        val itemsGifs = gifs[position]
        Glide.with(holder.imageView.context)
            .load(itemsGifs.previewUrl)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = gifs.size

    inner class GifsViewHolder(item: View, private val onGifSelected: (gif: Gif) -> Unit) :
        RecyclerView.ViewHolder(item) {
        private val binding = RecyclerviewItemBinding.bind(item)
        val imageView = binding.imageView

        init {
            itemView.setOnClickListener { onGifSelected(gifs[adapterPosition]) }
        }
    }
}