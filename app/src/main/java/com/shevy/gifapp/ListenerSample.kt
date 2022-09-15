package com.shevy.gifapp

import com.shevy.gifapp.domain.interactors.Gif

class ListenerSample(private val onClick: (gif: Gif) -> Unit) {

    fun blabla() {
//        onClick()
    }
}