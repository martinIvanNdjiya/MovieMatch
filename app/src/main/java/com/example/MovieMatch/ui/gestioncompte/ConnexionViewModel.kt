package com.example.MovieMatch.ui.gestioncompte

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.MovieMatch.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConnexionViewModel(val app: Application) : AndroidViewModel(app) {
    fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).login(email, password, onSuccess, onFailure)

        }
    }
}