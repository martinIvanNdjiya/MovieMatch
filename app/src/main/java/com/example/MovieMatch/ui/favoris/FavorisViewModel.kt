package com.example.MovieMatch.ui.favoris

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.MovieMatch.Repository
import com.example.MovieMatch.ui.dataclasses.favoris
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavorisViewModel(val app: Application) : AndroidViewModel(app) {

    // Liste des films favoris
    val listeFilms = MutableLiveData<Array<favoris>>()

    fun getFavoris() {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).getFavoris(listeFilms)
        }
    }

}
