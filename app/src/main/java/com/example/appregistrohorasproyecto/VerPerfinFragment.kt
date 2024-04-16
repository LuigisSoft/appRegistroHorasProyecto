package com.example.appregistrohorasproyecto

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.example.appregistrohorasproyecto.databinding.FragmentVerPerfinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class VerPerfinFragment : Fragment(){


    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: FragmentVerPerfinBinding
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerPerfinBinding.inflate(inflater, container, false)
        return binding.root


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){


        super.onViewCreated(view, savedInstanceState)
        val email = firebaseAuth.currentUser?.email
        val db = FirebaseFirestore.getInstance()

        val name=binding.nombreTexto
        val dniId=binding.dniTexto
        val phone=binding.telefonoTexto
        val empleado=binding.empleado
        val centro=binding.centro
        val fecha=binding.antiguedad
        val id=binding.id
        val jornadaAnual=binding.jornada


        db.collection("users").document(email.toString()).get().addOnSuccessListener { documentSnapshot ->
            val nombreFromDatabase = documentSnapshot.get("nombre") as String?
            val apellido1 = documentSnapshot.get("apellido") as String?
            val apellido2 = documentSnapshot.get("apellido2") as String?
            val dni = documentSnapshot.get("dni") as String?
            val telefono = documentSnapshot.get("telefono") as String?
            val categoria = documentSnapshot.get("Categoria") as String?
            val centroValue = documentSnapshot.get("centro") as String?
            val antiguedad = documentSnapshot.get("antiguedad") as String?
            val empleadoValue = documentSnapshot.get("empleado") as String?
            val jornada = documentSnapshot.get("jornada") as String?

            //concatenar
            val nombreCompleto = buildString {
                append("Nombre: ${nombreFromDatabase.orEmpty()}")
                if (!apellido1.isNullOrEmpty()) append(" $apellido1")
                if (!apellido2.isNullOrEmpty()) append(" $apellido2")

            }
            val dniC = "DNI: ${dni.orEmpty()}"
            val telefonoC = "Teléfono: ${telefono.orEmpty()}"
            val categoriaC = "Categoria: ${categoria.orEmpty()}"
            val centroC = "Centro: ${centroValue.orEmpty()}"
            val antiguedadC = "Antiguedad: ${antiguedad.orEmpty()}"
            val empleadoC = "Empleado: ${empleadoValue.orEmpty()}"
            val jornadaC = "Jornada: ${jornada.orEmpty()}"


            //testos en los Views
            name.setText(nombreCompleto)
            dniId.setText(dniC)
            phone.setText(telefonoC)
            name.setText(nombreCompleto)
            empleado.setText(categoriaC)
            centro.setText(centroC)
            fecha.setText(antiguedadC)
            id.setText(empleadoC)
            jornadaAnual.setText(jornadaC)

        }.addOnFailureListener() {exception ->
        Log.e("Tag", "Error obteniendo los datos: ", exception)

        }




    }

}





