package com.example.appregistrohorasproyecto


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import com.example.appregistrohorasproyecto.databinding.ActivityAuthBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthActivity : ComponentActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AppRegistroHorasProyecto)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Terminate this activity to prevent user from coming back
        }
        setup()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Terminate this activity to prevent user from coming back
        }
    }

    private fun setup() {
        val email = binding.textMail
        val password = binding.textContra
        binding.btnRegistrar.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        binding.btnAceptar.setOnClickListener {
            val emailString = email.text.toString()
            val passwordString = password.text.toString()

            if (emailString.isNotEmpty() && passwordString.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailString, passwordString)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish() // Terminate this activity after successful login
                        } else {
                            Toast.makeText(
                                this,
                                "Error al autenticar el usuario",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    this,
                    "Introduce contraseña o usuario",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.recupContra.setOnClickListener {
            startActivity(Intent(this, recuperarContra::class.java))
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
