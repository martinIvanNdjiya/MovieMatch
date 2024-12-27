package com.example.MovieMatch.ui.searchFriends

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.MovieMatch.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SearchFriendsFragment : Fragment() {

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
        val searchViewModel = ViewModelProvider(this)[FriendsSearchViewModel::class.java]

        // Gestion du bouton de retour
        view.findViewById<FloatingActionButton>(R.id.back_to_dash).setOnClickListener {
            view.findNavController().navigate(R.id.navigation_amis)
        }

        // Gestion de la barre de recherche
        val searchBar = view.findViewById<SearchView>(R.id.recherche)
        searchBar.queryHint = "Rechercher un ami" // Texte par défaut de la barre de recherche
        searchBar.isIconified = false // Pour afficher la barre de recherche au démarrage

        // Gestion de la recherche des utilisateurs
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            // Fonction appelée lorsque l'utilisateur tape dans la barre de recherche
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    searchViewModel.getUsersNotFriends()
                } else {
                    searchViewModel.filterUsers(newText) // Fonction appelée lorsque la recherche n'est pas vide
                }
                return false
            }
        })

        searchViewModel.friends.observe(viewLifecycleOwner) {
            searchViewModel.getUsersNotFriends() // Appel de la fonction pour récupérer la liste des utilisateurs non associés à un ami
        }

        // Gestion des films lors de la recherche
        searchViewModel.utilisateurs.observe(viewLifecycleOwner) {
            val rvUsers = view.findViewById<RecyclerView>(R.id.rv_search_films)
            val texteMessage = view.findViewById<TextView>(R.id.message)
            // Si la liste de films est vide, on affiche un message
            if (it == null) {
                texteMessage.text = "Aucun utilisateur"
                texteMessage.visibility = View.VISIBLE // Affichage du message lorsque la recherche est vide
            } else {
                texteMessage.visibility = View.INVISIBLE
                rvUsers.adapter = ListSearchFriendsRecyclerViewAdapter(it)
                rvUsers.adapter!!.notifyDataSetChanged()
                rvUsers.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }
}
