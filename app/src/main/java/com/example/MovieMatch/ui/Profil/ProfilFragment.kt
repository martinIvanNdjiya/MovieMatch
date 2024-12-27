package com.example.MovieMatch.ui.Profil

// import CircleTransform
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.MovieMatch.R
import com.squareup.picasso.Picasso
import androidx.activity.result.contract.ActivityResultContracts
import com.example.MovieMatch.ui.gestioncompte.AccueilActivity

class ProfilFragment : Fragment() {

    private lateinit var imageProfil: ImageView
    private lateinit var usernameEditText: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var pickImageLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profilViewModel = ViewModelProvider(this)[ProfilViewModel::class.java]

        usernameEditText = view.findViewById(R.id.usernameEditText)
        imageProfil = view.findViewById(R.id.ImageProfil)
        progressBar = view.findViewById(R.id.progressBar)

        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                progressBar.visibility = View.VISIBLE
                imageProfil.visibility = View.GONE

                // Load and upload the selected image
                Picasso.get().load(uri)
                    // .transform(CircleTransform())
                    .into(imageProfil, object : com.squareup.picasso.Callback {
                        override fun onSuccess() {
                            progressBar.visibility = View.GONE
                            imageProfil.visibility = View.VISIBLE
                            profilViewModel.uploadImageToStorage(uri)
                        }

                        override fun onError(e: Exception?) {
                            progressBar.visibility = View.GONE
                            imageProfil.setImageResource(R.drawable.defaultprofil)
                            imageProfil.visibility = View.VISIBLE
                        }
                    })
            }
        }

        // Observe the currentUser LiveData
        profilViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            progressBar.visibility = View.VISIBLE
            usernameEditText.setText(user.username)

            // Load the user's profile image
            Picasso.get().load(user.photoURL)
                .error(R.drawable.defaultprofil)
                // .transform(CircleTransform())
                .into(imageProfil, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        progressBar.visibility = View.GONE
                        imageProfil.setImageResource(R.drawable.defaultprofil)
                    }
                })
        }

        imageProfil.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        view.findViewById<View>(R.id.sauvegarder_username).setOnClickListener {
            val newUsername = usernameEditText.text.toString()
            profilViewModel.updateUsername(newUsername)

            // Reload the profile image after username update
            Picasso.get().load(profilViewModel.currentUser.value?.photoURL)
                // .transform(CircleTransform())
                .into(imageProfil)
        }

        view.findViewById<View>(R.id.logoutIcon).setOnClickListener {
            profilViewModel.logout(
                onSuccess = {
                    startActivity(Intent(requireContext(), AccueilActivity::class.java))
                },
                onFailure = { errorMessage ->
                    Toast.makeText(requireContext(), "Erreur de déconnexion: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            )
        }

        view.findViewById<View>(R.id.supprimer_compte).setOnClickListener {
            profilViewModel.deleteAccount(
                onSuccess = {
                    startActivity(Intent(requireContext(), AccueilActivity::class.java))
                },
                onFailure = { errorMessage ->
                    Toast.makeText(requireContext(), "Erreur de suppression de compte: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            )
        }

        // Fetch user information
        profilViewModel.fetchCurrentUserInfo()
    }
}
