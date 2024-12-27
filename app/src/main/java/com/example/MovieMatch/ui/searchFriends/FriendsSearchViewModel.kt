package com.example.MovieMatch.ui.searchFriends

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.MovieMatch.Repository
import com.example.MovieMatch.ui.dataclasses.Friends
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FriendsSearchViewModel (val app: Application): AndroidViewModel(app) {
    val tousUtilisateurs = MutableLiveData<Array<Friends>>() // Liste des utilisateurs
    val utilisateurs = MutableLiveData<Array<Friends>?>() // Liste des utilisateurs qui ne sont pas des amis de l'utilisateur courant
    val friends = MutableLiveData<Array<Friends>>() // Liste des amis de l'utilisateur courant

    init {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).getAllUsers(tousUtilisateurs)
            Repository(app).getFriends(friends)
        }
    }

    fun getFriends() {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).getFriends(friends)
        }
    }

    fun getUsersNotFriends() {
        viewModelScope.launch(Dispatchers.IO) {
            val friendsEmails = friends.value?.map { it.email } ?: emptyList()
            Log.d("TEST", "friendsEmails: $friendsEmails")
            val filteredUsers = tousUtilisateurs.value?.filter { it.email !in friendsEmails }?.toTypedArray()
            utilisateurs.postValue(filteredUsers)
            // Log.d("TEST", "filteredUsers: $filteredUsers")
        }
    }

    fun filterUsers(query: String) {
        val filteredList = utilisateurs.value?.filter { user ->
            user.username.contains(query, ignoreCase = true)
        }
        utilisateurs.postValue(filteredList?.toTypedArray())
    }
}
