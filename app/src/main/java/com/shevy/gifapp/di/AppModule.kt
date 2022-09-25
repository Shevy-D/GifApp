package com.shevy.gifapp.di

import com.shevy.gifapp.data.GifsApi
import com.shevy.gifapp.data.GifsInteractorImpl
import com.shevy.gifapp.domain.interactors.GifInteractor
import com.shevy.gifapp.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    viewModel { MainViewModel() }
    single<GifInteractor> { GifsInteractorImpl(get()) }
    single<Retrofit> {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(GifsApi.BASE_URL)
            .build()
    }
    single<GifsApi> {
        get<Retrofit>().create(GifsApi::class.java)
    }
}
