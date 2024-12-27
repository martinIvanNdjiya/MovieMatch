package com.example.MovieMatch.ui.jeu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.MovieMatch.Repository
import com.example.MovieMatch.ui.dataclasses.Results
import com.example.MovieMatch.ui.dataclasses.favoris
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class JeuViewModel (val app: Application): AndroidViewModel(app) {
    val jeuImage = MutableLiveData<Results>()
    val isFave = MutableLiveData<Boolean>()

    init {
        jeuF()
    }

    fun addFavoris(favoris: favoris) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).ajouterFavoris(favoris)
        }
    }

    fun jeuF() {
        viewModelScope.launch(Dispatchers.IO) {
            val filmIndex = Random.nextInt(0, 10)
            val page = Random.nextInt(1, 500)
            Repository(app).getFilmAleatoire(jeuImage, page, filmIndex)
            Repository(app).getIsFavoris(isFave, jeuImage.value?.id ?: 0)
        }
    }
}
