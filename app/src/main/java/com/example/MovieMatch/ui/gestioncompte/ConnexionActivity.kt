package com.example.MovieMatch.ui.gestioncompte

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.MovieMatch.MainActivity
import com.example.MovieMatch.R

class ConnexionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connexion)

        //verifier si utilisateur est connecter
//        RepositoryFireBase(application).getCurrentUser()?.let {
//            //utilisateur connecter
//            Toast.makeText(this, "Connecte!!!", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }

        // Initialiser le ViewModel
        val connexionViewModel = ViewModelProvider(this)[ConnexionViewModel::class.java]

        // Input + button
        val emailInput = findViewById<EditText>(R.id.connexionEmailInput)
        val passwordInput = findViewById<EditText>(R.id.connexionPasswordInput)
        val connexionBtn = findViewById<Button>(R.id.connexionBtn)



        // Lien si pas de compte
        val pasDeCompte = findViewById<TextView>(R.id.pasDeCompte)
        pasDeCompte.setOnClickListener {
            val intent = Intent(this, InscriptionActivity::class.java)
            startActivity(intent)
        }

        // ON CLICK SUR LE BOUTON CONNEXION
        connexionBtn.setOnClickListener {
            // Récupération des données
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            // Validation des champs
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validation du format de l'email
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Veuillez entrer un email valide", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Appel de la fonction de connexion
            connexionViewModel.login(email, password,
                onSuccess = {
                    // Connexion réussie
                    Toast.makeText(this, "Connexion réussie", Toast.LENGTH_SHORT).show()
                    // Redirection vers l'activité principale ou une autre activité
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                },
                onFailure = { errorMessage ->
                    // Échec de la connexion
                    Toast.makeText(this, "Erreur: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}
