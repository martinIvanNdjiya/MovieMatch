package com.example.MovieMatch.ui.friends//package com.example.MovieMatch.ui.friends

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
import com.example.MovieMatch.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FriendsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = LayoutInflater.from(context).inflate(R.layout.fragment_friends, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialisation du ViewModel
        val friendsViewModel = ViewModelProvider(this)[FriendsViewModel::class.java]
        friendsViewModel.getFriends()

        // Naviguer vers la recherche d'amis
        view.findViewById<FloatingActionButton>(R.id.searchButton).setOnClickListener {
            view.findNavController().navigate(R.id.navigation_search_friends)
        }

        // Affichage des amis de l'utilisateur dans la page amis
        friendsViewModel.friends.observe(viewLifecycleOwner){
            if (it.isNullOrEmpty()) {
                view.findViewById<TextView>(R.id.info).visibility = View.VISIBLE
            } else {
                // attaccher les données au recyclerView
                view.findViewById<TextView>(R.id.info).visibility = View.INVISIBLE
                val rvFriends = view.findViewById<RecyclerView>(R.id.rvFriends)
                rvFriends.layoutManager = LinearLayoutManager(this.activity)
                rvFriends.adapter = ListFriendsRecyclerViewAdapter(it)
            }
        }
    }
}
