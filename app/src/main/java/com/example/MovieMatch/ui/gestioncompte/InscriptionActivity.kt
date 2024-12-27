package com.example.MovieMatch.ui.gestioncompte

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.MovieMatch.MainActivity
import com.example.MovieMatch.R

class InscriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inscription)

        // Initialiser le ViewModel
        val inscriptionViewModel = ViewModelProvider(this)[InscriptionViewModel::class.java]

        // Input + button
        val usernameInput = findViewById<EditText>(R.id.InputUsername)
        val emailInput = findViewById<EditText>(R.id.InputEmail)
        val passwordInput = findViewById<EditText>(R.id.InputPassword)
        val inputConfirmPassword = findViewById<EditText>(R.id.InputConfirmPassword)
        val inscriptionBtn = findViewById<Button>(R.id.Inscription)
        val photoUrl = "gs://moviematch-33ef3.appspot.com/profile_images/defaultprofil.png"

        // Lien si possede deja un  compte
        val dejaUnCompte = findViewById<TextView>(R.id.dejaUnCompte)
        dejaUnCompte.setOnClickListener {
            val intent = Intent(this, ConnexionActivity::class.java)
            startActivity(intent)
        }

        // ON CLICK SUR LE BOUTON INSCRIPTION
        inscriptionBtn.setOnClickListener {
            // Récupération des données
            val username = usernameInput.text.toString()
            val email = emailInput.text.toString().lowercase()
            val password = passwordInput.text.toString()
            val confirmPassword = inputConfirmPassword.text.toString()

            // Validation des entrées
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //VERRIFIE SI LES MOTS DE PASSE SONT IDENTIQUES
            if (password != confirmPassword) {
                passwordInput.text.clear()
                inputConfirmPassword.text.clear()
                Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Afficher un message de chargement
            Toast.makeText(this, "Inscription en cours...", Toast.LENGTH_SHORT).show()

            // Appel de la fonction d'inscription
            inscriptionViewModel.register(
                username,
                email,
                password,
                photoUrl, // photoURL par défaut
                onSuccess = {
                    // Inscription réussie
                    Toast.makeText(this, "Inscription réussie", Toast.LENGTH_SHORT).show()

                    // Connexion
                    inscriptionViewModel.login(
                        email,
                        password,
                        onSuccess = {
                            // Connexion réussie
                            Toast.makeText(this, "Connexion réussie", Toast.LENGTH_SHORT).show()
                            // Redirection vers l'activité principale
                            startActivity(Intent(this, MainActivity::class.java))
                            finish() // Fermer l'activité d'inscription
                        },
                        onFailure = { errorMessage ->
                            // Échec de la connexion
                            Toast.makeText(this, "Erreur: $errorMessage", Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                onFailure = { errorMessage ->
                    // Échec de l'inscription
                    Toast.makeText(this, "Erreur: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}
