package com.shevy.gifapp.presentation.detail

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class DownloadBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d("MyBroadcastReceiver", " ПРИШЛО УВЕДОМЛЕНИЕ!!!")
    }
}