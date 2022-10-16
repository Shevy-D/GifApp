package com.shevy.gifapp.di

import android.app.Application
import com.shevy.gifapp.data.GifsApi
import com.shevy.gifapp.data.GifsInteractorImpl
import com.shevy.gifapp.domain.interactors.GifInteractor
import com.shevy.gifapp.presentation.main.MainViewModel
import com.shevy.gifapp.room.repository.FavoriteRepository
import com.shevy.gifapp.room.viewmodel.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val favoriteModule = module {
    viewModel<FavoriteViewModel> { FavoriteViewModel(application = get()) }
}
