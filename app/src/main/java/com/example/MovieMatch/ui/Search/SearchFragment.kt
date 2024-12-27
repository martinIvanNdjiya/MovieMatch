package com.example.MovieMatch.ui.Search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.MovieMatch.R
import com.example.MovieMatch.ui.tableauBord.FilmsRecyclerViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        val v = LayoutInflater.from(context).inflate(R.layout.fragment_search, container, false)
        return v
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Récupération du ViewModel
        val searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        searchViewModel.getPopularMovies() // Récupération des films populaires

        // Gestion du bouton de retour
        view.findViewById<FloatingActionButton>(R.id.back_to_dash).setOnClickListener {
            view.findNavController().navigate(R.id.navigation_tableau_bord)
        }

        // Gestion de la barre de recherche
        val searchBar = view.findViewById<SearchView>(R.id.recherche)
        searchBar.queryHint = "Rechercher un film" // Texte par défaut de la barre de recherche
        searchBar.isIconified = false // Pour afficher la barre de recherche au démarrage

        // Gestion des changements de texte dans la barre de recherche
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    searchViewModel.getPopularMovies() // Fonction appelée lorsque la recherche est vide
                } else {
                    searchViewModel.searchMovies(newText) // Fonction appelée lorsque la recherche n'est pas vide
                }
                return false
            }
        })

        // Gestion des films lors de la recherche
        searchViewModel.listeFilms.observe(viewLifecycleOwner) {
            val rvSearchFilms = view.findViewById<RecyclerView>(R.id.rv_search_films)
            val texteMessage = view.findViewById<TextView>(R.id.message)

            // Si la liste de films est vide, on affiche un message
            if (it.results.isEmpty()) {
                texteMessage.text = "Aucun résultats"
                texteMessage.visibility = View.VISIBLE // Affichage du message lorsque la recherche est vide
            } else {
                var listeFilms = it.results.filter { film -> film.poster_path != null }.toTypedArray()
                texteMessage.visibility = View.INVISIBLE
                rvSearchFilms.adapter = FilmsRecyclerViewAdapter(listeFilms)
                rvSearchFilms.adapter!!.notifyDataSetChanged()
                rvSearchFilms.layoutManager = GridLayoutManager(view.context, 3)
            }
        }

        // Gestion des films populaires lors de l'initiation de la page
        searchViewModel.listeFilmsOfTheDay.observe(viewLifecycleOwner) {
            var listeFilms = it.results.filter { film -> film.poster_path != null }.toTypedArray()
            val rvSearchFilms = view.findViewById<RecyclerView>(R.id.rv_search_films)
            rvSearchFilms.adapter = FilmsRecyclerViewAdapter(listeFilms)
            rvSearchFilms.adapter!!.notifyDataSetChanged()
            rvSearchFilms.layoutManager = GridLayoutManager(view.context, 3)
        }
   }
}
