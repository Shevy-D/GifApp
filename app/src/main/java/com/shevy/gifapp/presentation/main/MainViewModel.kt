package com.shevy.gifapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevy.gifapp.domain.interactors.Gif
import com.shevy.gifapp.domain.interactors.GifInteractor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val interactor: GifInteractor) : ViewModel() {

/*    private val _searchText = MutableLiveData<String>()
    private val _loading = MutableLiveData<Boolean>()
    private val _gifs = MutableLiveData<List<Gif>>()*/
    private val _searchText: MutableStateFlow<String> = MutableStateFlow("")
    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _gifs: MutableStateFlow<List<Gif>> = MutableStateFlow(listOf())

    val searchText: StateFlow<String> = _searchText
    val loading: StateFlow<Boolean> = _loading
    val gifs: MutableStateFlow<List<Gif>> = _gifs

    fun onSearchTextChanged(text: String) {
        if (_searchText.value == text) return

        /*_searchText.postValue(text)
        _loading.postValue(true)
        viewModelScope.launch {
            val gifs = interactor.getSearchingGifs(text).await()
            _gifs.postValue(gifs)

            //delay(1000)

            _loading.postValue(false)*/

        _searchText.value = text
        _loading.value = true
        viewModelScope.launch {
            val gifs = interactor.getSearchingGifs(text).await()
            _gifs.value = gifs

            //for imitation of working
            delay(1000)

            _loading.value = false
        }
    }
}