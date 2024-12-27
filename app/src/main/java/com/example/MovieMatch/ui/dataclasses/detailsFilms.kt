package com.example.MovieMatch.ui.dataclasses

// Classe pour stocker les informations détaillés d'un film
data class detailsFilms(
    val adult               : Boolean? = null,
    val backdrop_path        : String? = null,
    val budget              : Int? = null,
    val genres              : ArrayList<Genres> = arrayListOf(),
    val homepage            : String? = null,
    val id                  : Int? = null,
    val imdb_id              : String? = null,
    val original_language    : String? = null,
    val original_title       : String? = null,
    val overview            : String? = null,
    val popularity          : Double? = null,
    val poster_path         : String? = null,
    val production_companies : ArrayList<ProductionCompanies> = arrayListOf(),
    val production_countries : ArrayList<ProductionCountries> = arrayListOf(),
    val release_date         : String? = null,
    val revenue             : Int? = null,
    val runtime             : Int? = null,
    val spoken_languages     : ArrayList<SpokenLanguages> = arrayListOf(),
    val tag_line             : String? = null,
    val title               : String? = null,
    val video               : Boolean? = null,
    val vote_average         : Double? = null,
    val vote_count           : Int? = null
)
