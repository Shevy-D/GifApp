package com.shevy.gifapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevy.gifapp.domain.interactors.Gif
import com.shevy.gifapp.domain.interactors.GifInteractor
import kotlinx.coroutines.launch

class MainViewModel(private val interactor: GifInteractor) : ViewModel() {

    private val _searchText = MutableLiveData<String>()
    private val _loading = MutableLiveData<Boolean>()
    private val _gifs = MutableLiveData<List<Gif>>()

    val searchText: LiveData<String> = _searchText
    val loading: LiveData<Boolean> = _loading
    val gifs: LiveData<List<Gif>> = _gifs

    fun onSearchTextChanged(text: String) {
        if (_searchText.value == text) return

        _searchText.postValue(text)
        _loading.postValue(true)
        viewModelScope.launch {
            val gifs = interactor.getSearchingGifs(text).await()
            _gifs.postValue(gifs)
            _loading.postValue(false)
        }
    }
}