// JeuFragment.kt
package com.example.MovieMatch.ui.jeu

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.MovieMatch.MainActivity
import com.example.MovieMatch.R
import com.example.MovieMatch.Repository
import com.example.MovieMatch.ui.dataclasses.favoris
import com.squareup.picasso.Picasso

class JeuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_jeux, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // récupérer viewmodel
        val jeuViewModel = ViewModelProvider(this)[JeuViewModel::class.java]

        // Récupération des éléments de vue
        val posterImageView = view.findViewById<ImageView>(R.id.jeu_poster)
        val likeButton = view.findViewById<ImageView>(R.id.like)
        val titleTextButton = view.findViewById<Button>(R.id.getDetails)
        val dislikeButton = view.findViewById<ImageView>(R.id.dislike)

        // afficher image bouton
        likeButton.setBackgroundResource(R.drawable.like)
        dislikeButton.setBackgroundResource(R.drawable.dislike)

        // Valider si le film est un favoris
        jeuViewModel.isFave.observe(viewLifecycleOwner) {
            if (it) {
                jeuViewModel.jeuF()
            }
        }

        // Chargement de l'image du film et du titre
        jeuViewModel.jeuImage.observe(viewLifecycleOwner) {
            Picasso.get().load(MainActivity.IMGURL + it.poster_path).into(posterImageView)
            titleTextButton.text = it.title
            val id = it.id
            titleTextButton.setOnClickListener {
                val param = bundleOf(Pair("id", id)) // Envoi de l'id du film à la page de détails
                view.findNavController().navigate(R.id.navigation_details, param)
            }
        }

        // diriger vers page favoris
        likeButton.setOnClickListener {
            jeuViewModel.jeuImage.value?.let {
                val fav = jeuViewModel.jeuImage.value?.id?.let { it1 -> jeuViewModel.jeuImage.value?.poster_path?.let { it2 -> favoris(it1, it2) } }
                jeuViewModel.addFavoris(fav!!)
                jeuViewModel.jeuF()
            }
        }

        // nouveau film au clique
        dislikeButton.setOnClickListener {
            jeuViewModel.jeuF()
        }
    }
}
