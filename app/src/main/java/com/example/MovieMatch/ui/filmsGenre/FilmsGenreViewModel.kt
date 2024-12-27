package com.example.MovieMatch.ui.filmsGenre

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.MovieMatch.Repository
import com.example.MovieMatch.ui.dataclasses.ListeFilms
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilmsGenreViewModel(val app: Application) : AndroidViewModel(app) {

    // Liste des films d'un genre
    val listeFilms = MutableLiveData<ListeFilms>()

    // Récupération des films d'un genre
    fun getFilms(genreId: Int, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).getFilms(genreId, page, listeFilms)
        }
    }
}
