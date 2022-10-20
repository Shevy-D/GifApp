package com.shevy.gifapp.presentation.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shevy.gifapp.R
import com.shevy.gifapp.databinding.RecyclerviewItemBinding
import com.shevy.gifapp.data.models.database.Favorite

class FavoritesAdapter(
private val onFavoriteSelected: ((favorites: Favorite) -> Unit)
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    //private val favorites = mutableListOf<Favorite>()
    var favorites = emptyList<Favorite>()

    fun setFavorite(newFavorites: List<Favorite>) {
        favorites = newFavorites
/*        favorites.apply {
            clear()
            addAll(newFavorites)
        }*/
        notifyDataSetChanged()
    }

    //class FavoritesViewHolder(item: View) : RecyclerView.ViewHolder(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false),
            onFavoriteSelected
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val itemsGifs = favorites[position]
        Glide.with(holder.imageView.context)
            .load(itemsGifs.downsized)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = favorites.size

    inner class FavoritesViewHolder(
        item: View,
        private val onFavoriteSelected: (favorite: Favorite) -> Unit
    ) :
        RecyclerView.ViewHolder(item) {
        private val binding = RecyclerviewItemBinding.bind(item)
        val imageView = binding.imageView

        init {
            itemView.setOnClickListener { onFavoriteSelected(favorites[adapterPosition]) }
        }
    }
}