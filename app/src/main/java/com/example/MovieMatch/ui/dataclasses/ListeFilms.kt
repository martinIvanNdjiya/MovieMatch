package com.example.MovieMatch.ui.dataclasses

// Classe pour stocker une liste de films
data class ListeFilms (
    val page : Int,
    val results : ArrayList<Results> ,
    val totalPages : Int,
    val totalResults : Int
)
