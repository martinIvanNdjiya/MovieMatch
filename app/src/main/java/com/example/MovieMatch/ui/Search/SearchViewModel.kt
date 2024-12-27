package com.example.MovieMatch.ui.Search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.MovieMatch.Repository
import com.example.MovieMatch.ui.dataclasses.ListeFilms
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(val app: Application) : AndroidViewModel(app) {

    val listeFilms = MutableLiveData<ListeFilms>() // Liste des films recherchés
    val listeFilmsOfTheDay = MutableLiveData<ListeFilms>() // Liste des films populaires de la journée

    // Fonction pour récupérer les films populaires
    fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).getPopularMovies(listeFilmsOfTheDay)
        }
    }

    // Fonction pour rechercher des films
    fun searchMovies(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).searchMovies(query, listeFilms)
        }
    }
}

