package com.example.MovieMatch.ui.gestioncompte
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.MovieMatch.R

class AccueilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.accueil)


        //variable
        val btnDirectionConnexion = findViewById<Button>(R.id.btnDirectionConnexion)
        val btnInscription = findViewById<Button>(R.id.btnDirectionInscription)

        //bouton connexion
        btnDirectionConnexion.setOnClickListener {
            val intent = Intent(this, ConnexionActivity::class.java)
            startActivity(intent)
        }
        //bouton inscription
        btnInscription.setOnClickListener {
            val intent = Intent(this, InscriptionActivity::class.java)
            startActivity(intent)
        }
}
}