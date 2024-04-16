package com.example.appregistrohorasproyecto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.appregistrohorasproyecto.databinding.FragmentModPerflBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class modPerflFragment : Fragment() {

    private lateinit var binding: FragmentModPerflBinding
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModPerflBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = firebaseAuth.currentUser?.email
        val db = FirebaseFirestore.getInstance()

        val name = binding.nombre
        val lastName = binding.apellido
        val lastName2 = binding.apellido2
        val adress = binding.direccion
        val dniId = binding.dni
        val phone = binding.telefono

        binding.btnModificar.setOnClickListener {
            val updateMap = mutableMapOf<String, Any?>()
            if (name.text.isNotEmpty()) updateMap["nombre"] = name.text.toString()
            if (lastName.text.isNotEmpty()) updateMap["apellido"] = lastName.text.toString()
            if (lastName2.text.isNotEmpty()) updateMap["apellido2"] = lastName2.text.toString()
            if (adress.text.isNotEmpty()) updateMap["direccion"] = adress.text.toString()
            if (dniId.text.isNotEmpty()) updateMap["dni"] = dniId.text.toString()
            if (phone.text.isNotEmpty()) updateMap["telefono"] = phone.text.toString()


            //actualizamos solo los campos que rellenemos del documento, dejanto sin tocar los que tengan el espacio en blanco
            if (updateMap.isNotEmpty()) {
                db.collection("users").document(email.toString())
                    .update(updateMap)
                    .addOnCompleteListener {
                        val toast = Toast.makeText(
                            requireContext(),
                            "Datos modificados",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()


                    }
                    .addOnFailureListener {
                        Log.e("Prueba", "No se han podido actualizar los datos")
                    }

            }
        }


        binding.volverMenu.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

    }
}