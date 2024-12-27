package com.example.MovieMatch.ui.friends

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.MovieMatch.Repository
import com.example.MovieMatch.ui.dataclasses.Friends
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FriendsViewModel (val app: Application): AndroidViewModel(app) {
    val friends = MutableLiveData<Array<Friends>>() // Liste des amis

    // Récupération des amis de l'utilisateur courant
    fun getFriends() {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).getFriends(friends)
        }
    }
}
