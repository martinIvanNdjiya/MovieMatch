package com.example.MovieMatch.ui.favoris

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.MovieMatch.R

class FavorisFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = LayoutInflater.from(context).inflate(R.layout.fragment_favoris, container, false)
        return v
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Récupération des données du ViewModel
        val favorisViewModel =
            ViewModelProvider(this)[FavorisViewModel::class.java]

        // Récupération des films favoris
        favorisViewModel.getFavoris()

        // Affichage des genres
        favorisViewModel.listeFilms.observe(viewLifecycleOwner) {
            // Mettre à jour l'interface avec les données
            val rvFavoris = view.findViewById<RecyclerView>(R.id.rvFavoris)
            rvFavoris.adapter = FavorisRecyclerViewAdapter(it)
            rvFavoris.adapter!!.notifyDataSetChanged()
            rvFavoris.layoutManager = GridLayoutManager(this.activity, 3)
        }
    }
}
