package com.example.MovieMatch.ui.tableauBord

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.MovieMatch.MainActivity
import com.example.MovieMatch.R
import com.example.MovieMatch.ui.dataclasses.Results
import com.squareup.picasso.Picasso

class FilmsRecyclerViewAdapter(val listeFilms: Array<Results>) :
    RecyclerView.Adapter<FilmsRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.films_items, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Affichage de l'image du film
        val imgFilm = holder.itemView.findViewById<ImageView>(R.id.filmIMG)
        Picasso.get().load(MainActivity.IMGURL + listeFilms[position].poster_path).into(imgFilm)

        // Navigation vers la page de détails du film
        imgFilm.setOnClickListener {
            val param = bundleOf(Pair("id", listeFilms[position].id)) // Envoi de l'id du film à la page de détails
            holder.itemView.findNavController().navigate(R.id.navigation_details, param)
        }
    }

    override fun getItemCount(): Int {
        return listeFilms.size
    }
}