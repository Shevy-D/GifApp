package com.shevy.gifapp.di

import com.shevy.gifapp.room.viewmodel.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel<FavoriteViewModel> { FavoriteViewModel(application = get()) }
}
