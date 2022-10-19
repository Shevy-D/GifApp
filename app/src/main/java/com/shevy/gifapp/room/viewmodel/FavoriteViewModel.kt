package com.shevy.gifapp.room.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.shevy.gifapp.room.database.FavoriteDatabase
import com.shevy.gifapp.room.model.Favorite
import com.shevy.gifapp.room.repository.FavoriteRepository
import com.shevy.gifapp.room.repository.FavoriteRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) :
    AndroidViewModel(application) {

    lateinit var repository: FavoriteRepository
    val context = application

    fun initDatabase(){
        val daoNote = FavoriteDatabase.getInstance(context).getFavoriteDao()
        repository = FavoriteRepositoryImpl(daoNote)
    }

    fun getAllFavorites(): LiveData<List<Favorite>>{
        return repository.allFavorites
    }

    fun insertFavorite(favorite: Favorite, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavorite(favorite) {
                onSuccess()
            }
        }
    }

    fun deleteFavorite(favorite: Favorite, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(favorite) {
                onSuccess()
            }
        }
    }

    fun deleteAllFavorite(onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll {
                onSuccess()
            }
        }
    }

/*
    private val _allFavorites = MutableLiveData<List<Favorite>>()
    //private val _favorite = MutableLiveData<Favorite>()

    val allFavorites: LiveData<List<Favorite>> = _allFavorites
    //val favorite: LiveData<Favorite> = _favorite

    private var favoriteRepositoryImpl: FavoriteRepositoryImpl =
        FavoriteRepositoryImpl(FavoriteDatabase.getInstance(application).getFavoriteDao())
*/

    /*suspend fun insertFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepositoryImpl.insertFavorite(favorite)
        }
    }

    suspend fun deleteFavorite(favorite: Favorite) {
        favoriteRepositoryImpl.deleteFavorite(favorite)
    }

    suspend fun deleteAllFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepositoryImpl.deleteAll()
        }
    }*/
}