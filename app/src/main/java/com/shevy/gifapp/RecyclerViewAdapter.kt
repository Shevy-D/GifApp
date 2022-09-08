package com.shevy.gifapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shevy.gifapp.data.Data
import com.shevy.gifapp.databinding.RecyclerviewItemBinding

class RecyclerViewAdapter(private val context: Context, private val gifs: List<Data>) :
    RecyclerView.Adapter<RecyclerViewAdapter.GifsViewHolder>() {

    lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }


    class GifsViewHolder(item: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(item) {

        private val binding = RecyclerviewItemBinding.bind(item)
        val imageView = binding.imageView

        init {
            item.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifsViewHolder {
        return GifsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false),
            mListener
        )
    }

    override fun onBindViewHolder(holder: GifsViewHolder, position: Int) {
        val itemsGifs = gifs[position]
        //Picasso.get().load(itemsGifs.images.original.url).into(holder.imageView)
        Glide.with(context).load(itemsGifs.images.original.url).into(holder.imageView)
    }

    override fun getItemCount(): Int = gifs.size
}