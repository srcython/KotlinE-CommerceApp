package com.sercancelik.turkcellgygfinal

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application(){
//    override fun onCreate() {
//        super.onCreate()
//        FirebaseApp.initializeApp(this)
//    }
}