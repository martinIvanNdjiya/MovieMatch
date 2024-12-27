package com.example.MovieMatch

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseUser
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    companion object {
        const val SRVURL = "https://api.themoviedb.org/3/"
        const val APIKEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkYzUxZDgwYzA2NDllZWI1ZmZmMjRkYWRjNjEyNjJkMCIsIm5iZiI6MTcyODQwNDI1NC4zODc2MzIsInN1YiI6IjY2Y2RkYmNlNjRlNTc3MDc5N2JkMTczZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.meiEyScg0cHuzo8WLQXXMyK-W2lSIPqA5okr0VcovEw"
        const val IMGURL = "https://image.tmdb.org/t/p/w500"
        val page = Random.nextInt(1, 10) // Génère un nombre aléatoire entre 1 et 10
        var currentUser: FirebaseUser? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Repository(application).authentificationAPI()

        setContentView(R.layout.activity_main)

        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
