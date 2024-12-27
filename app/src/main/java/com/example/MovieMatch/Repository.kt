package com.example.MovieMatch

import android.app.Application
import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.MovieMatch.MainActivity.Companion.SRVURL
import com.example.MovieMatch.ui.dataclasses.Friends
import com.example.MovieMatch.ui.dataclasses.ListeFilms
import com.example.MovieMatch.ui.dataclasses.ListeGenres
import com.example.MovieMatch.ui.dataclasses.Results
import com.example.MovieMatch.ui.dataclasses.User
import com.example.MovieMatch.ui.dataclasses.detailsFilms
import com.example.MovieMatch.ui.dataclasses.favoris
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson

class Repository(val app: Application) {

    // Initialiser FirebaseAuth
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun authentificationAPI() {
        val q = Volley.newRequestQueue(app)
        val r = object : StringRequest(
            Method.POST,
            "${SRVURL}authentication",
            {
            },
            {
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headerMap = mutableMapOf<String, String>()
                headerMap.put("Content-Type", "application/json")
                headerMap.put("Authorization", "Bearer ${MainActivity.APIKEY}")
                return headerMap
            }
        }
        q.add(r)
    }

    // Récupération des genres
    fun getGenres(genres: MutableLiveData<ListeGenres>) {
        val q = Volley.newRequestQueue(app)
        val r = object: StringRequest(
            Method.GET,
            "${MainActivity.SRVURL}genre/movie/list?language=fr",
            {
                val gre = Gson().fromJson(it, ListeGenres::class.java)
                genres.postValue(gre)
            },
            {
                Toast.makeText(app, "Erreur", Toast.LENGTH_LONG).show()
            }
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headerMap = mutableMapOf<String, String>()
                headerMap.put("Content-Type", "application/json")
                headerMap.put("Authorization", "Bearer ${MainActivity.APIKEY}")
                return headerMap
            }
        }
        q.add(r)
    }
    // Récupération des films d'un genre
    fun getFilms(genreId: Int, page: Int, films: MutableLiveData<ListeFilms>) {
        val q = Volley.newRequestQueue(app)
        val r = object: StringRequest(
            Method.GET,
            "${MainActivity.SRVURL}discover/movie?language=fr-CA&page=${page}&sort_by=popularity.desc&with_genres=${genreId}",
            {
                val film = Gson().fromJson(it, ListeFilms::class.java)
                films.postValue(film)
            },
            {
                Toast.makeText(app, "Erreur", Toast.LENGTH_LONG).show()
            }
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headerMap = mutableMapOf<String, String>()
                headerMap.put("Content-Type", "application/json")
                headerMap.put("Authorization", "Bearer ${MainActivity.APIKEY}")
                return headerMap
            }
        }
        q.add(r)
    }

    // Récupération d'un film aléatoire
    fun getFilmAleatoire(films: MutableLiveData<Results>, page: Int, index: Int) {
        val q = Volley.newRequestQueue(app)
        val r = object: StringRequest(
            Method.GET,
            "${SRVURL}discover/movie?language=fr-CA&page=${page}&sort_by=popularity.desc'",
            {
                val listeFilms = Gson().fromJson(it, ListeFilms::class.java)
                films.postValue(listeFilms.results[index])
            },
            {
                Toast.makeText(app, "Erreur", Toast.LENGTH_LONG).show()
            })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headerMap = mutableMapOf<String, String>()
                headerMap.put("Content-Type", "application/json")
                headerMap.put("Authorization", "Bearer ${MainActivity.APIKEY}")
                return headerMap
            }
        }
        q.add(r)
    }



    // Récupération des détails d'un film
    fun getDetailsFilms(films: MutableLiveData<detailsFilms>, filmId: Int) {
        val q = Volley.newRequestQueue(app)
        val r = object : StringRequest(
            Method.GET,
            "${SRVURL}movie/${filmId}?language=fr-CA",
            {
                val la = Gson().fromJson(it, detailsFilms::class.java)
                films.postValue(la)
            },
            {}
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headerMap = mutableMapOf<String, String>()
                headerMap.put("Content-Type", "application/json")
                headerMap.put("Authorization", "Bearer ${MainActivity.APIKEY}")
                return headerMap
            }
        }
        q.add(r)
    }

    // Fonction pour rechercher des films
    fun searchMovies(query: String, films: MutableLiveData<ListeFilms>) {
        val q = Volley.newRequestQueue(app)
        val r = object : StringRequest(
            Method.GET,
            "${MainActivity.SRVURL}search/movie?query=${query}&language=fr-CA&page=1",
            {
                val film = Gson().fromJson(it, ListeFilms::class.java)
                films.postValue(film)
            },
            {
                Toast.makeText(app, "Erreur", Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headerMap = mutableMapOf<String, String>()
                headerMap.put("Content-Type", "application/json")
                headerMap.put("Authorization", "Bearer ${MainActivity.APIKEY}")
                return headerMap
            }
        }
        q.add(r)
    }
    // Fonction pour récupérer les films populaires
    fun getPopularMovies(films: MutableLiveData<ListeFilms>) {
        val q = Volley.newRequestQueue(app)
        val r = object : StringRequest(
            Method.GET,
            "${MainActivity.SRVURL}trending/movie/day?language=fr-CA",
            {
                val film = Gson().fromJson(it, ListeFilms::class.java)
                films.postValue(film)
            },
            {
                Toast.makeText(app, "Erreur", Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headerMap = mutableMapOf<String, String>()
                headerMap.put("Content-Type", "application/json")
                headerMap.put("Authorization", "Bearer ${MainActivity.APIKEY}")
                return headerMap
            }
        }
        q.add(r)
    }

    // Fonction qui récupère les films favoris d'un utilisateur
    fun getFavoris(fav: MutableLiveData<Array<favoris>>) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            Firebase.firestore.collection("Utilisateurs")
                .document(uid) // id de l'User quand il va se connecter
                .collection("favorisFilms")
                .get()
                .addOnSuccessListener {
                    val favList = mutableListOf<favoris>()
                    for (document in it) {
                        // Convert each document to a User object
                        favList.add(document.toObject(favoris::class.java))
                        fav.postValue(favList.toTypedArray())
                    }
                }
                .addOnFailureListener { e -> Log.w(TAG, "Error getting the document", e) }
        }
    }

    // Fonction qui récupère si le film est un film favori
    fun getIsFavoris(fav: MutableLiveData<Boolean>, filmId: Int) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            Firebase.firestore.collection("Utilisateurs")
                .document(uid) // id de l'User quand il va se connecter
                .collection("favorisFilms")
                .document(filmId.toString())
                .get()
                .addOnSuccessListener { document ->
                    Log.d("documentFav", "DocumentSnapshot data: $document")
                    if (document.exists()) {
                        fav.postValue(true)
                    } else {
                        fav.postValue(false)
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error getting the document", e)
                }
        }
    }

    fun ajouterFavoris(film: favoris) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            Firebase.firestore.collection("Utilisateurs")
                .document(uid) // id de l'User quand il va se connecter
                .collection("favorisFilms")
                .document(film.id.toString())
                .set(film)
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }
    }

    fun supprimerFavoris(filmId: Int) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            Firebase.firestore.collection("Utilisateurs")
                .document(uid) // id de l'User quand il va se connecter
                .collection("favorisFilms")
                .document(filmId.toString())
                .delete()
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully deleted!")
                }
                .addOnFailureListener {
                    e -> Log.w(TAG, "Error deleting document", e)
                }
        }
    }

    fun getFriends(friends: MutableLiveData<Array<Friends>>) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            Firebase.firestore.collection("Utilisateurs")
                .document(uid)
                .collection("friends")
                .get()
                .addOnSuccessListener { result ->
                    val friendList = mutableListOf<Friends>()
                    for (document in result) {
                        // Convert each document to a User object
                        friendList.add(document.toObject(Friends::class.java))
                    }
                    friends.postValue(friendList.toTypedArray())
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error getting all the documents", e)
                }
            }
        }

    fun getAllUsers(utilisateurs: MutableLiveData<Array<Friends>>) {
        val db = Firebase.firestore
        Log.d("getFriends", "auth.currentUser?.email: ${auth.currentUser?.email}")
        db.collection("Utilisateurs")
            .whereNotEqualTo("email", auth.currentUser?.email)
            .get()
            .addOnSuccessListener { result ->
                val users = mutableListOf<Friends>()

                for (document in result) {
                    users.add(document.toObject(Friends::class.java))
                }
                Log.d("getFriends", "users: $users")
                utilisateurs.postValue(users.toTypedArray())
            }
            .addOnFailureListener { e ->
                Log.w("TEST", "Error getting all the documents", e)
            }
    }

    fun addFriend(user: Friends) {
        val db = Firebase.firestore
        db.collection("Utilisateurs")
            .document(auth.currentUser?.uid ?: "")
            .collection("friends")
            .document(user.email)
            .set(user)
            .addOnSuccessListener { documentReference -> Log.d(TAG, "DocumentSnapshot written!") }
            .addOnFailureListener{ e -> Log.w(TAG, "Error adding document", e) }
    }

    fun removeFriend(email: String) {
        val db = Firebase.firestore
        db.collection("Utilisateurs")
            .document(auth.currentUser?.uid ?: "")
            .collection("friends")
            .document(email)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "Document successfully deleted!") }
            .addOnFailureListener{ e -> Log.w(TAG, "Error adding document", e) }
    }

    // Inscription
    fun register(username: String, email: String, password: String, photoURL: String?, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Récupérer l'UID de l'utilisateur nouvellement créé
                    val uid = auth.currentUser?.uid
                    // Créer un objet utzilisateur
                    val user = hashMapOf(
                        "username" to username,
                        "email" to email,
                        "photoURL" to photoURL
                    )
                    // Ajouter l'utilisateur à Firestore
                    uid?.let {
                        firestore.collection("Utilisateurs").document(it).set(user)
                            .addOnSuccessListener {
                                onSuccess()
                            }
                            .addOnFailureListener { e ->
                                onFailure("Erreur lors de l'ajout à Firestore: ${e.message}")
                            }
                    } ?: onFailure("Erreur : UID utilisateur non trouvé")
                } else {
                    // Échec de l'inscription
                    task.exception?.let {
                        onFailure(it.message ?: "Erreur lors de l'inscription")
                    }
                }
            }
    }
    // Connexion
    fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Succès de la connexion
                    MainActivity.currentUser = auth.currentUser
                    onSuccess()
                } else {
                    // Échec de la connexion
                    task.exception?.let {
                        onFailure(it.message ?: "Erreur lors de la connexion")
                    }
                }
            }
    }
    //
    fun getCurrentUserInfo(onSuccess: (User) -> Unit, onFailure: (String) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            firestore.collection("Utilisateurs").document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Convertir le document Firestore en objet User
                        val user = document.toObject(User::class.java)
                        user?.let {
                            onSuccess(it)
                        } ?: onFailure("Erreur lors de la récupération des données de l'utilisateur")
                    } else {
                        onFailure("Aucun document trouvé pour l'utilisateur actuel")
                    }
                }
                .addOnFailureListener { e ->
                    onFailure("Erreur lors de la récupération des données de l'utilisateur: ${e.message}")
                }
        } else {
            onFailure("Erreur : UID utilisateur non trouvé")
        }
    }
    // Modifier le nom d'utilisateur
    fun updateUsername(username: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            firestore.collection("Utilisateurs").document(uid)
                .update("username", username)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener { exception ->
                    // Call onFailure with the error message
                    onFailure(exception.message ?: "Unknown error occurred")
                }
        } else {
            // Handle the case where the user is not authenticated
            onFailure("User is not authenticated")
        }
    }
    // Modifier Photo Profil
    fun updatePhotoURL(photoURL: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val uid = auth.currentUser?.uid
        if(uid != null) {
            firestore.collection("Utilisateurs").document(uid)
                .update("photoURL", photoURL)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener { exception ->
                    // Call onFailure with the error message
                    onFailure(exception.message ?: "Unknown error occurred")
                }
        }
        else{
            // Handle the case where the user is not authenticated
            onFailure("User is not authenticated")
        }
    }
    fun uploadImageToStorage(uri: Uri, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("profile_images/$uid.jpg") // Path where the image will be stored
            imageRef.putFile(uri)
                .addOnSuccessListener {
                    // Get the download URL
                    imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                        // Update the photo URL in Firestore
                        updatePhotoURL(downloadUrl.toString(),
                            onSuccess = {
                                // Handle success
                                onSuccess()
                            },
                            onFailure = { errorMessage ->
                                // Handle failure
                                onFailure(errorMessage)
                            }
                        )
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle upload failure
                    onFailure("Upload failed: ${exception.message}")
                }
        } else {
            onFailure("User is not authenticated")
        }
    }
    // Déconnexion
    fun logout(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        auth.signOut()
        onSuccess()
    }
    //Suprimer compte
    fun deleteAccount(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            firestore.collection("Utilisateurs").document(uid)
                .delete()
                .addOnSuccessListener {
                    onSuccess() // Call the success callback when the account is deleted
                }
                .addOnFailureListener { exception ->
                    // Call the failure callback with the error message
                    onFailure(exception.message ?: "An unknown error occurred")
                }
        } else {
            onFailure("User not found")
        }
    }
}
