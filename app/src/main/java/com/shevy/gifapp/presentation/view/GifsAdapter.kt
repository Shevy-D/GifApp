package com.shevy.gifapp.presentation.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shevy.gifapp.R
import com.shevy.gifapp.data.Gifs
import com.shevy.gifapp.databinding.RecyclerviewItemBinding
import com.shevy.gifapp.domain.interactors.Gif

class GifsAdapter(
    private val gifs: List<Gifs>
    //private val listener: OnItemClickListener,
    //added it
    //private val onGifSelected: (gif: Gif) -> Unit
) :
    RecyclerView.Adapter<GifsAdapter.GifsViewHolder>() {

    //added it
/*    private val gifs = mutableListOf<Gif>()

    @SuppressLint("NotifyDataSetChanged")
    fun setGifs(gifs: List<Gif>) {
        this.gifs.apply {
            clear()
            addAll(gifs)
        }
        notifyDataSetChanged()
    }*/


    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class GifsViewHolder(item: View) :
        RecyclerView.ViewHolder(item) {
        private val binding = RecyclerviewItemBinding.bind(item)
        val imageView = binding.imageView
/*        init {
            item.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifsViewHolder {
        return GifsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GifsViewHolder, position: Int) {
        val itemsGifs = gifs[position]
        Glide.with(holder.imageView.context).load(itemsGifs.images.downsized.url).into(holder.imageView)
    }

    override fun getItemCount(): Int = gifs.size
}