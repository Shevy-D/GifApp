package com.shevy.gifapp.presentation.favorite

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shevy.gifapp.databinding.ActivityFavoriteBinding
import com.shevy.gifapp.presentation.detail.DetailActivity
import com.shevy.gifapp.room.model.Favorite
import com.shevy.gifapp.room.viewmodel.FavoriteViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoriteBinding

    //private val favoriteViewModel by viewModel<FavoriteViewModel>()

    private val adapter = FavoritesAdapter(::onClick)

    private lateinit var favoriteViewModel: FavoriteViewModel

    private fun onClick(favorite: Favorite) {
        val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
        intent.putExtra("url", favorite.original)
        intent.putExtra("previewUrl", favorite.downsized)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val testButton = binding.testButton
        val recyclerView = binding.favoritesRecyclerView

        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.adapter = adapter

        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        favoriteViewModel.initDatabase()

        lifecycleScope.launch {
            //val favorites = favoriteViewModel.allFavorites

/*            val favorites_test = listOf<Favorite>(
                Favorite(
                    0,
                    downsized = "https://media3.giphy.com/media/gZQ7NsdTXM86ZLPpdN/giphy-downsized.gif?cid=f491d3b4hubhkuj6x7029ygp1b96bf16bw1t8q84myzqewf9&rid=giphy-downsized.gif&ct=g",
                    original = "https://media3.giphy.com/media/gZQ7NsdTXM86ZLPpdN/giphy.gif?cid=f491d3b4hubhkuj6x7029ygp1b96bf16bw1t8q84myzqewf9&rid=giphy.gif&ct=g"
                ),
                Favorite(
                    0,
                    downsized = "https://media3.giphy.com/media/gZQ7NsdTXM86ZLPpdN/giphy-downsized.gif?cid=f491d3b4hubhkuj6x7029ygp1b96bf16bw1t8q84myzqewf9&rid=giphy-downsized.gif&ct=g",
                    original = "https://media3.giphy.com/media/gZQ7NsdTXM86ZLPpdN/giphy.gif?cid=f491d3b4hubhkuj6x7029ygp1b96bf16bw1t8q84myzqewf9&rid=giphy.gif&ct=g"
                ),
                Favorite(
                    0,
                    downsized = "https://media2.giphy.com/media/cCuBNLbZqclxkpWyby/giphy-downsized.gif?cid=f491d3b4hubhkuj6x7029ygp1b96bf16bw1t8q84myzqewf9&rid=giphy-downsized.gif&ct=g",
                    original = "https://media2.giphy.com/media/cCuBNLbZqclxkpWyby/giphy.gif?cid=f491d3b4hubhkuj6x7029ygp1b96bf16bw1t8q84myzqewf9&rid=giphy.gif&ct=g"
                )
            )*/
            favoriteViewModel.getAllFavorites().observe(this@FavoriteActivity) { listFavorites ->
                adapter.setFavorite(listFavorites.asReversed())
            }
        }

        testButton.setOnClickListener {
            Toast.makeText(this, "Test toast", Toast.LENGTH_SHORT).show()
        }


    }
}