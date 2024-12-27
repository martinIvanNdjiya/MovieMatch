package com.example.MovieMatch.ui.Profil

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.MovieMatch.Repository
import com.example.MovieMatch.ui.dataclasses.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfilViewModel(val app: Application) : AndroidViewModel(app) {

     val currentUser = MutableLiveData<User>()

    fun fetchCurrentUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).getCurrentUserInfo(
                onSuccess = { user ->
                    currentUser.postValue(user)
                },
                onFailure = { errorMessage ->
                }

            )
        }
    }

    fun updateUsername(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).updateUsername(username,
                onSuccess = {
                    currentUser.postValue(currentUser.value?.copy(username = username))
                },
                onFailure = { errorMessage ->
                })
        }
    }

    // Modifier Photo Profil et sauvegarder dans Firestore
    fun uploadImageToStorage(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository(app).uploadImageToStorage(uri,
                onSuccess = {
                    currentUser.postValue(currentUser.value?.copy(photoURL = uri.toString()))
                },
                onFailure = { errorMessage -> }
            )
        }
    }

    // Déconnexion
    fun logout(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        Repository(app).logout(onSuccess, onFailure)
    }
    // Supprimer compte
    fun deleteAccount(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        Repository(app).deleteAccount(onSuccess, onFailure)
    }
}
