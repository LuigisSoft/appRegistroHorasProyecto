package com.example.appregistrohorasproyecto

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appregistrohorasproyecto.databinding.ActivityAuthBinding
import com.example.appregistrohorasproyecto.databinding.ActivityRecuperarContraBinding
import com.example.appregistrohorasproyecto.databinding.FragmentVerHorasBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class recuperarContra : AppCompatActivity(){

    private lateinit var binding: ActivityRecuperarContraBinding
    private lateinit var auth: FirebaseAuth
    private var firebaseAuth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_recuperar_contra)


        val recuperar=findViewById<Button>(R.id.button_recup)
        var email=findViewById<TextView>(R.id.editTextTextEmailAddress)

        recuperar.setOnClickListener{
            sendPasswordReset(email.text.toString())
        }



        val volver = findViewById<Button>(R.id.button_volver)
        volver.setOnClickListener{
            intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

    }
        private fun sendPasswordReset(email: String){
            if(email.isNotBlank()){
                firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            val toast =Toast.makeText(this, "correo de recuperación enviado a tu email", Toast.LENGTH_SHORT).show()
                        }else{
                            val toast =Toast.makeText(this, "error al  enviar correo de recuperación", Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                val toast =Toast.makeText(this, "Debe ingresar un correo electrónico", Toast.LENGTH_SHORT).show()

            }

}




    }

