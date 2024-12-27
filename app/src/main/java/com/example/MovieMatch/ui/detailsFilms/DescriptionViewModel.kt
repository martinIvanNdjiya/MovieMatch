package com.example.MovieMatch.ui.detailsFilms

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.MovieMatch.Repository
import com.example.MovieMatch.ui.dataclasses.detailsFilms
import com.example.MovieMatch.ui.dataclasses.favoris
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DescriptionViewModel (val app: Application): AndroidViewModel(app) {
    val detailsFilms = MutableLiveData<detailsFilms>() // LiveData pour stocker les détails du film
    val fav = MutableLiveData<Boolean>() // LiveData pour savoir si le film est un favori

    // Fonction pour récupérer les détails d'un film
    fun detailsF(filmId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).getDetailsFilms(detailsFilms, filmId)
        }
    }

    // Fonction pour vérifier si un film est un favori
    fun isFav(filmId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).getIsFavoris(fav, filmId)
        }
    }

    // Fonction pour supprimer un film des favoris
    fun delFav(filmId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).supprimerFavoris(filmId)
        }
    }

    // Fonction pour ajouter un film aux favoris
    fun addFav(favoris: favoris) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).ajouterFavoris(favoris)
        }
    }
}