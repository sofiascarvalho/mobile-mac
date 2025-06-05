package br.senai.sp.jandira.reporterdomeubairromac

import android.app.Application
import com.google.firebase.FirebaseApp

class MeuApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}