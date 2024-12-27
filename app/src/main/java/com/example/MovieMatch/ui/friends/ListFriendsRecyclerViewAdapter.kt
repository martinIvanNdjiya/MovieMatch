package com.example.MovieMatch.ui.friends

import android.app.Application
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

class ListFriendsRecyclerViewAdapter(val listeFriends: Array<Friends>?)
    : RecyclerView.Adapter<ListFriendsRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_friends, parent, false) as View
        return ViewHolder(view)
    }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       if (listeFriends?.get(position)?.photoURL.isNullOrEmpty()) {
           holder.view.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.defaultprofil)
       } else {
           Picasso.get().load(listeFriends?.get(position)?.photoURL).into(holder.view.findViewById<ImageView>(R.id.imageView))
       }
       holder.view.findViewById<TextView>(R.id.nomF).text = listeFriends?.get(position)?.username ?: "Anonyme"
       holder.view.findViewById<Button>(R.id.remove_friend).visibility = View.VISIBLE

       // Fonction d'ajout d'un ami
       holder.view.findViewById<Button>(R.id.add_friend).setOnClickListener {
           // Ajouter l'ami
           Repository(holder.itemView.context.applicationContext as Application).addFriend(listeFriends?.get(position) ?: Friends())
           holder.view.findNavController().navigate(R.id.navigation_search_friends)
       }

       // Fonction de suppression d'un ami
       holder.view.findViewById<Button>(R.id.remove_friend).setOnClickListener {
           // Supprimer l'ami
           Repository(holder.itemView.context.applicationContext as Application).removeFriend(listeFriends?.get(position)?.email ?: "")
           holder.view.findNavController().navigate(R.id.navigation_amis)
       }
   }

    override fun getItemCount(): Int {
        return listeFriends?.size ?: 0
    }
}
