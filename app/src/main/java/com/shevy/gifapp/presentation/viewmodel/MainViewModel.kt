package com.shevy.gifapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(
    /*val getUserNameUseCase: GetUserNameUseCase,
    val saveUserNameUseCase: SaveUserNameUseCase*/
) : ViewModel() {

    var resultLiveMutable = MutableLiveData<String>()
    //val resultLive: LiveData<String> = resultLiveMutable

/*    fun save(text: String) {
        val param = SaveUserNameParam(name = text)
        val resultData: Boolean = saveUserNameUseCase.execute(param)
        resultLive.value = "Save result = $resultData"
    }

    fun load() {
        val userName: UserName = getUserNameUseCase.execute()
        resultLive.value = "${userName.firstName} ${userName.lastName}"
    }*/
}