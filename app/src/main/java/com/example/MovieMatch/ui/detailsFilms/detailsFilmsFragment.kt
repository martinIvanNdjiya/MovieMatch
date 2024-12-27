package com.example.MovieMatch.ui.detailsFilms

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.MovieMatch.MainActivity
import com.example.MovieMatch.R
import com.example.MovieMatch.ui.dataclasses.favoris
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class DetailsFilmsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = LayoutInflater.from(context).inflate(R.layout.fragment_description, container, false)
        return v
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Récupération de l'ID du film depuis les arguments
        val filmId = this.requireArguments().getInt("id")

        // Initialisation du ViewModel
        val descriptionViewModel = ViewModelProvider(this)[DescriptionViewModel::class.java]
        descriptionViewModel.detailsF(filmId) // Récupération des détails du film

        // Gestion du bouton de retour
        view.findViewById<FloatingActionButton>(R.id.back).setOnClickListener {
            view.findNavController().popBackStack()
        }

        descriptionViewModel.detailsFilms.observe(viewLifecycleOwner) {
            // attaccher les données
            view.findViewById<TextView>(R.id.titre).text = it.title
            Picasso.get().load(MainActivity.IMGURL + it.backdrop_path).into(view.findViewById<ImageView>(R.id.poster))
            view.findViewById<TextView>(R.id.duree).text = it.runtime.toString() + " minutes"
            view.findViewById<TextView>(R.id.annee).text = it.release_date
            view.findViewById<TextView>(R.id.Genres).text = it.genres.joinToString{it.name}

            // Vérifier si le film a une image de fond
            if (it.backdrop_path != null) {
                Picasso.get().load(MainActivity.IMGURL + it.backdrop_path).into(view.findViewById<ImageView>(R.id.poster))
            } else {
                Picasso.get().load(MainActivity.IMGURL + it.poster_path).into(view.findViewById<ImageView>(R.id.poster))
            }

            // Vérifier si la description n'est pas vide avant de l'afficher
            if (it.overview != "") {
                view.findViewById<TextView>(R.id.Description).text = it.overview
            } else {
                view.findViewById<TextView>(R.id.Description).text = "Aucune description n'est disponible pour ce film."
            }

            val film = it
            // Vérifier si le film est déjà dans les favoris
            view.findViewById<Button>(R.id.add_favoris).setOnClickListener {
                val fav = film.id?.let { it1 -> film.poster_path?.let { it2 -> favoris(it1, it2) } }
                descriptionViewModel.addFav(fav!!)
                film.id?.let { it1 -> descriptionViewModel.isFav(it1) }
            }
            view.findViewById<Button>(R.id.remove_favoris).setOnClickListener {
                film.id?.let { it1 -> descriptionViewModel.delFav(it1) }
                film.id?.let { it1 -> descriptionViewModel.isFav(it1) }
            }

            it.id?.let { it1 -> descriptionViewModel.isFav(it1) }
        }

        descriptionViewModel.fav.observe(viewLifecycleOwner) {
            // Mettre à jour l'interface en fonction du statut du film
            if (it == true) {
                view.findViewById<Button>(R.id.add_favoris).visibility = View.INVISIBLE
                view.findViewById<Button>(R.id.remove_favoris).visibility = View.VISIBLE
            } else {
                view.findViewById<Button>(R.id.remove_favoris).visibility = View.INVISIBLE
                view.findViewById<Button>(R.id.add_favoris).visibility = View.VISIBLE
            }
        }
    }

}