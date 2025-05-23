package com.example.firebasesigninwithmanualdi

import android.app.Application
import com.example.firebasesigninwithmanualdi.di.AppContainer
import com.example.firebasesigninwithmanualdi.di.DefaultAppContainer

class FirebaseSignInApplication: Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()

        appContainer = DefaultAppContainer(this)
    }

}