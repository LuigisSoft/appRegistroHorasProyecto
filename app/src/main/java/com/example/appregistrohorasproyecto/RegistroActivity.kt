package com.example.appregistrohorasproyecto

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appregistrohorasproyecto.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val name = binding.nombre
        val lastName = binding.apellido
        val lastName2 = binding.apellido2
        val address = binding.direccion
        val dniId = binding.dni
        val phone = binding.telefono
        val buttonRegistrar = binding.btnRegistrar
        buttonRegistrar.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(
                    this,
                    "El correo electrónico no tiebe yb firnati válido",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (!isValidPassword(password)) {

            }
            if (!isValidPassword(password)) {
                Toast.makeText(
                    this,
                    "La contraseña debe tener como mínimo una mayúscula. un número y un símbolo",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if(task.isSuccessful){
                    Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT)
                        .show()

                    val userRef = db.collection("users").document(email)
                    userRef.set(
                        hashMapOf(
                            "nombre" to name.text.toString(),
                            "apellido" to lastName.text.toString(),
                            "apellido2" to lastName2.text.toString(),
                            "dirección" to address.text.toString(),
                            "dni" to dniId.text.toString(),
                            "telefono" to phone.text.toString()
                        )
                    )
                    auth.signOut()
                }else {

            Toast.makeText(this, "error al registrar el usuario", Toast.LENGTH_SHORT).show()

        }
        }
    }
    binding.volverLogin.setOnClickListener{
        intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
    }
}

    private fun isValidPassword(password: String): Boolean {

        val uppercaseRegex = Regex("[A-Z]")
        val numberRegex = Regex("[0-9]")
        val specialCharRegex = Regex("[!@#\$%^&*()_+={}|:;'\"<>,.?/\\-]")
        return uppercaseRegex.containsMatchIn(password) &&
                numberRegex.containsMatchIn(password) &&
                specialCharRegex.containsMatchIn(password)

    }
}

