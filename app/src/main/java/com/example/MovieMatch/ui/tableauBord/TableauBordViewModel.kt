package com.example.MovieMatch.ui.tableauBord

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.MovieMatch.Repository
import com.example.MovieMatch.ui.dataclasses.ListeFilms
import com.example.MovieMatch.ui.dataclasses.ListeGenres
import com.example.MovieMatch.ui.dataclasses.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TableauBordViewModel(val app: Application) : AndroidViewModel(app) {

    val listeGenres = MutableLiveData<ListeGenres>() // Liste des genres
    val listeFilms = MutableLiveData<ListeFilms>() // Liste des films d'un genre
    val currentUser = MutableLiveData<User>()

    // Récupération des genres lors de l'initiation
    init {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).getGenres(listeGenres)
        }
    }

    // Récupération des films d'un genre
    fun getFilms(genreId: Int, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).getFilms(genreId, page, listeFilms)
        }
    }

    fun fetchCurrentUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).getCurrentUserInfo(
                onSuccess = { user ->
                    currentUser.postValue(user)
                },
                onFailure = { errorMessage ->
                    // Gérer l'erreur si nécessaire
                }
            )
        }
    }
    
}
