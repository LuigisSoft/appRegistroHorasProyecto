package com.example.appregistrohorasproyecto

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.appregistrohorasproyecto.databinding.FragmentNominasBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.Calendar

class FragmentNominas : Fragment() {
    private lateinit var binding: FragmentNominasBinding
    private val firebaseAuth = FirebaseAuth.getInstance()

    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNominasBinding.inflate(inflater, container, false)
        binding = FragmentNominasBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()

        //rellenar spineryear
        val spinnerYear = view?.findViewById<Spinner>(R.id.spinnerYear)
        if (spinnerYear != null) {

        } else {
            //uso este if porque me esta dando un error en el spinner
            Log.e("FramentNominas", "error spinner")
        }
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val years = (currentYear downTo currentYear - 10).map { it.toString() }
        val yearAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //por sugerencia del android studio
        if (spinnerYear != null) {
            spinnerYear.adapter = yearAdapter
        }

        //rellenamos spinner mes
        val spinnerMonth = view?.findViewById<Spinner>(R.id.spinnerMonth)
        if (spinnerMonth != null) {
            //sugeremcia andoid studio por fallo
        } else {
            //uso este if porque me esta dando un error en el spinner
            Log.e("FramentNominas", "error spinner")
        }
        val months = arrayOf(
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
            "Octubre", "Noviembre", "Diciembre"
        )

        val monthAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, months)
        monthAdapter.setDropDownViewResource((android.R.layout.simple_spinner_dropdown_item))

        //por sugerencia del android studio
        if (spinnerMonth != null) {
            spinnerMonth.adapter = monthAdapter
        }

        binding.buttonPulsar.setOnClickListener {
            val currentUser = auth.currentUser
            currentUser?.let {
                currentUser.email?.let { it1 ->
                    val selectedYear = spinnerYear?.selectedItem.toString().toInt()
                    val selectedMonth = (spinnerMonth?.selectedItemPosition?.plus(1)
                        ?: 1) //los meses comienzan desde 1

                    downloadFile(it1, selectedYear, selectedMonth)
                }
            }
        }

        return view
    }

    private fun downloadFile(email: String, selectedYear: Int, selectedMonth: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val userSnapshot = FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(email)
                    .get()
                    .await()

                if (userSnapshot.exists()) {
                    val empleado = userSnapshot.getString("empleado")
                    val fileName = "$empleado-$selectedYear-$selectedMonth.pdf"

                    Log.d("Prueba", "Descargado: $fileName")
                    val storageRef = storage.reference.child((fileName))
                    val localFile = File(requireContext().getExternalFilesDir(null), fileName)

                    storageRef.getFile(localFile)
                        .addOnSuccessListener {
                            Log.d("Prueba", "Archivo descargado correctamente: $fileName")
                            val toast= Toast.makeText(requireContext(),"El documento se ha descargado", Toast.LENGTH_SHORT).show()


                        }
                        .addOnFailureListener { exception ->
                            Log.e("Prueba", "Error al descargar el archivo", exception)
                        }
                } else {
                    Log.e("Prueba", "Usuario no encontrado en la base de datos")
                }

            } catch (e: Exception) {
                Log.e("Prueba", "Error al obtener el documento", e)
            }
        }
    }
}
















