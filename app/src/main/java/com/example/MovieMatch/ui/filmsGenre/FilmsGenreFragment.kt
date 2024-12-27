package com.example.MovieMatch.ui.filmsGenre

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.MovieMatch.MainActivity
import com.example.MovieMatch.R
import com.example.MovieMatch.ui.tableauBord.FilmsRecyclerViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.random.Random

class FilmsGenreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = LayoutInflater.from(context).inflate(R.layout.fragment_films_categorie, container, false)
        return v
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Liste des pages déjà affichées
        var listePage = ArrayList<Int>()
        listePage.add(MainActivity.page)

        // Récupération des données du ViewModel
        val filmsGenreViewModel = ViewModelProvider(this)[FilmsGenreViewModel::class.java]

        // Récupération de l'id du genre
        val categorieId = this.requireArguments().getInt("id")
        // Récupération du nom de la catégorie
        val nomCategorie = this.requireArguments().getString("nom")

        // Affichage d'autres films de la catégorie
        view.findViewById<Button>(R.id.more_movies).setOnClickListener {
            getNewPageItems(categorieId, filmsGenreViewModel, listePage)
        }

        // Affichage du nom de la catégorie
        view.findViewById<TextView>(R.id.nomCatgeorie).text = nomCategorie

        // Gestion du bouton de retour
        val backButton = view.findViewById<FloatingActionButton>(R.id.back_to_dash)
        backButton.setOnClickListener {
            view.findNavController().navigate(R.id.navigation_tableau_bord)
        }

        // Affichage des films de la catégorie
        filmsGenreViewModel.listeFilms.observe(viewLifecycleOwner) {
            var listeFilms = it.results.filter { film -> film.poster_path != null }.toTypedArray()
            val rvFilmsGenre = view.findViewById<RecyclerView>(R.id.rv_films_cat)
            rvFilmsGenre.adapter = FilmsRecyclerViewAdapter(listeFilms)
            rvFilmsGenre.adapter!!.notifyDataSetChanged()
            rvFilmsGenre.layoutManager = GridLayoutManager(view.context, 3)
        }

        // Récupération des films de la catégorie lors de l'initiation
        getNewPageItems(categorieId, filmsGenreViewModel, listePage)
    }

    fun getNewPageItems(categorieId: Int, filmsGenreViewModel: FilmsGenreViewModel, listePage: ArrayList<Int>) {
        val page = Random.nextInt(1, 50)

        if (page !in listePage) {
            filmsGenreViewModel.getFilms(categorieId, page)
            listePage.add(page)
        }
    }

}
