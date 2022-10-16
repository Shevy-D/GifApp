package com.shevy.gifapp.di

import com.shevy.gifapp.data.GifsApi
import com.shevy.gifapp.data.GifsInteractorImpl
import com.shevy.gifapp.domain.interactors.GifInteractor
import com.shevy.gifapp.presentation.main.MainViewModel
import com.shevy.gifapp.room.viewmodel.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    viewModel { MainViewModel(GifsInteractorImpl(get())) }  /*GifsInteractorImpl(get())*/
    single<GifInteractor> { GifsInteractorImpl(get()) }
    //singleOf(::GifsInteractorImpl) { bind<GifInteractor>()}  можно и так делать
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
