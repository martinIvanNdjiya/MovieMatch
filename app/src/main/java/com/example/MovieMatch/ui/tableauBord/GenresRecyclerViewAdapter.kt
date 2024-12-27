package com.example.MovieMatch.ui.tableauBord

import android.annotation.SuppressLint
import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.MovieMatch.MainActivity
import com.example.MovieMatch.R
import com.example.MovieMatch.ui.dataclasses.Genres

class GenresRecyclerViewAdapter(val listeGenres: ArrayList<Genres>) :
    RecyclerView.Adapter<GenresRecyclerViewAdapter.ViewHolder>() {

        class ViewHolder(v: View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.genre_items, parent, false)
        return ViewHolder(v)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Affichage du nom du genre
        holder.itemView.findViewById<TextView>(R.id.genreName).text = listeGenres[position].name

        // Récupération des films du genre
        val tableauBordViewModel = TableauBordViewModel(holder.itemView.context.applicationContext as Application)
        tableauBordViewModel.getFilms(listeGenres[position].id, MainActivity.page)

        // Navigation vers plus de films du genre
        holder.itemView.findViewById<TextView>(R.id.more).setOnClickListener {
            val bundle = bundleOf(Pair("nom", listeGenres[position].name), Pair("id", listeGenres[position].id))
            holder.itemView.findNavController().navigate(R.id.navigation_films_genre, bundle)
        }

        // Affichage des films du genre
        tableauBordViewModel.listeFilms.observe(holder.itemView.context as MainActivity) {
            var listeFilms = (it.results.filter { film -> film.poster_path !== null }).toTypedArray()
            val rvFilms = holder.itemView.findViewById<RecyclerView>(R.id.rv_films)
            rvFilms.adapter = FilmsRecyclerViewAdapter(listeFilms)
            rvFilms.adapter!!.notifyDataSetChanged()
            rvFilms.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
            holder.itemView.findViewById<ProgressBar>(R.id.loading).visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return listeGenres.size
    }

}
