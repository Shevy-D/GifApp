package com.shevy.gifapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val searchText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}