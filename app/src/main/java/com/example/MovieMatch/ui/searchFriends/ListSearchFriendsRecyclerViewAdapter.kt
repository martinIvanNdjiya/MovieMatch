package com.example.MovieMatch.ui.searchFriends

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.MovieMatch.R
import com.example.MovieMatch.Repository
import com.example.MovieMatch.ui.dataclasses.Friends
import com.squareup.picasso.Picasso

class ListSearchFriendsRecyclerViewAdapter(val listeUsers: Array<Friends>?)
    : RecyclerView.Adapter<ListSearchFriendsRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_friends, parent, false) as View
        return ViewHolder(view)
    }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       if (listeUsers?.get(position)?.photoURL.isNullOrEmpty()) {
           holder.view.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.defaultprofil)
       } else {
           Picasso.get().load(listeUsers?.get(position)?.photoURL).into(holder.view.findViewById<ImageView>(R.id.imageView))
       }
       holder.view.findViewById<TextView>(R.id.nomF).text = listeUsers?.get(position)?.username ?: "Anonyme"
       Log.d("statusAmis", listeUsers?.get(position)?.status ?: "null")

       if (listeUsers?.get(position)?.status === "wait") {

           holder.view.findViewById<Button>(R.id.add_friend).visibility = View.INVISIBLE
           holder.view.findViewById<Button>(R.id.remove_friend).visibility = View.VISIBLE

           // Fonction de suppression d'un ami
           holder.view.findViewById<Button>(R.id.remove_friend).setOnClickListener {
               // Supprimer l'ami
               Repository(holder.itemView.context.applicationContext as Application).removeFriend(listeUsers?.get(position)?.email ?: "")
               holder.view.findNavController().navigate(R.id.navigation_search_friends)
           }
       } else {
           holder.view.findViewById<Button>(R.id.add_friend).visibility = View.VISIBLE
           holder.view.findViewById<Button>(R.id.remove_friend).visibility = View.INVISIBLE

           // Fonction d'ajout d'un ami
           holder.view.findViewById<Button>(R.id.add_friend).setOnClickListener {
               // Ajouter l'ami
               listeUsers?.get(position)?.status = "wait"
               Repository(holder.itemView.context.applicationContext as Application).addFriend(listeUsers?.get(position) ?: Friends())
               holder.view.findNavController().navigate(R.id.navigation_search_friends)
           }
       }
   }

    override fun getItemCount(): Int {
        return listeUsers?.size ?: 0
    }
}
