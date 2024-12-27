package com.example.MovieMatch.ui.gestioncompte

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.MovieMatch.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InscriptionViewModel(val app: Application) : AndroidViewModel(app) {


    // Fonction pour gérer l'inscription
    fun register(
        username: String,
        email: String,
        password: String,
        photoURL: String?,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).register(username, email, password, photoURL, onSuccess, onFailure)
        }
    }


    fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).login(email, password, onSuccess, onFailure)
        }
    }
}