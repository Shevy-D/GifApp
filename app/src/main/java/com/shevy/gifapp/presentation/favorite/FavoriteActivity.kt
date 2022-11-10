package com.shevy.gifapp.presentation.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.internal.ContextUtils.getActivity
import com.shevy.gifapp.data.models.database.Favorite
import com.shevy.gifapp.databinding.ActivityFavoriteBinding
import com.shevy.gifapp.presentation.detail.DetailActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoriteBinding

    private val adapter = FavoritesAdapter(::onClick)

    private val favoriteViewModel by viewModel<FavoriteViewModel>()

    private fun onClick(favorite: Favorite) {
        val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
        intent.putExtra("url", favorite.original)
        intent.putExtra("previewUrl", favorite.downsized)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater).also { setContentView(it.root) }
        //setContentView(binding.root)

        getActivity(this@FavoriteActivity)?.title = "Favorites"

        val testButton = binding.testButton
        val recyclerView = binding.favoritesRecyclerView

        recyclerView.layoutManager =
            GridLayoutManager(this@FavoriteActivity, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        favoriteViewModel.initDatabase()

/*        lifecycleScope.launch {
            favoriteViewModel.getAllFavorites().observe(this@FavoriteActivity) { listFavorites ->
                adapter.setFavorite(listFavorites.asReversed())
            }
        }*/

        lifecycleScope.launchWhenStarted {
            favoriteViewModel.getAllFavorites().collect { listFavorites ->
                adapter.setFavorite(listFavorites.asReversed())
            }
        }

        testButton.setOnClickListener {
            favoriteViewModel.deleteAllFavorites()
        }
    }
}