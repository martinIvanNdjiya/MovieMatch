package com.example.MovieMatch.ui.tableauBord

import android.annotation.SuppressLint
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.MovieMatch.MainActivity
import com.example.MovieMatch.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TableauBordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = LayoutInflater.from(context).inflate(R.layout.fragment_tableau_bord, container, false)
        return v
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Récupération des données du ViewModel
        val tableauBordViewModel = ViewModelProvider(this)[TableauBordViewModel::class.java]

        //FETCH LES INFO DE L'UTILISATEURS
        tableauBordViewModel.fetchCurrentUserInfo()

        // Navigation vers la page de recherche
        view.findViewById<FloatingActionButton>(R.id.searchButton).setOnClickListener {
            view.findNavController().navigate(R.id.navigation_search)
        }

        // Message d'accueil
        tableauBordViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            // Affichage du message d'accueil avec le nom d'utilisateur
            view.findViewById<TextView>(R.id.username).text = "Bonjour, ${user.username}"
        }
        // Affichage des genres
        tableauBordViewModel.listeGenres.observe(viewLifecycleOwner) {
            val rvGenres = view.findViewById<RecyclerView>(R.id.rv_genres)
            rvGenres.adapter = GenresRecyclerViewAdapter(it.genres)
            rvGenres.adapter!!.notifyDataSetChanged()
            rvGenres.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        }
    }
}
