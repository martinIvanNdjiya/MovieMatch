package com.example.MovieMatch.ui.dataclasses

// Classe pour stocker les informations d'un utilisateur
data class User(
    val username: String = "",
    val email: String = "",
    val photoURL: String? = null,
    val friends: ArrayList<Friends>? = ArrayList()
)
