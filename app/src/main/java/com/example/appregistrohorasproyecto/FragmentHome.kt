package com.example.appregistrohorasproyecto

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.appregistrohorasproyecto.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FragmentHome : Fragment(){
    lateinit var  toogle : ActionBarDrawerToggle

    private lateinit var binding: FragmentHomeBinding
    private val firebaseAuth = FirebaseAuth.getInstance()
    private var entradaActiva = false

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated (view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)


        //obtenemos el usuario mediante el correo electrónico
        val de = FirebaseFirestore.getInstance()
        val email = firebaseAuth.currentUser?.email

        //código para los botones
        //boton de entrasa
        binding.button2.setOnClickListener{
            miBotonEntrada()
            val now = LocalDateTime.now()
            val formateo= DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")
            val horaDia = formateo.format(now)
            binding.entrada.text="$horaDia"
            binding.entrada.setPadding(20,10,10,10)
        }

        //boton de salida
        binding.button3.setOnClickListener{
            miBotonSalida()
            val now = LocalDateTime.now()
            val formateo= DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")
            val horaDia = formateo.format(now)
            binding.salida.text="$horaDia"
            binding.salida.setPadding(20,10,10,10)
        }

    }


    // funciones de los botones de entrada y salida

    private fun miBotonEntrada(){
        //obtenemos la fecha y hora actual
        val datosEntrada = LocalDateTime.now()

        //convierte la fecha y hora en un formato legible
        val formatoFechaHora = DateTimeFormatter.ofPattern("dd-MM-yy / HH:mm")
        val fechaHoraFormateada = datosEntrada.format(formatoFechaHora)

        val email = firebaseAuth.currentUser?.email
        val db = FirebaseFirestore.getInstance()

        //crea documento en la coleccion entradas con el ID del usuario
         val documento = db.collection("entradas").document(email.toString())

        //obtiene el documento existente
        documento.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documentExiste = task.result
                if (documentExiste.exists()) {
                    //obtiene campo HorasRegistradas"
                    val horasRegistradas =
                        documentExiste.get("HorasRegistradas") as? MutableList<String>
                            ?: mutableListOf()

                    //agrega la nueva hora al final de la liststa
                    horasRegistradas.add(fechaHoraFormateada)

                    //actualiza el campo "HorasRegistradas"
                    documento.update("HorasRegistradas", horasRegistradas)
                } else {
                    //crea documento con la nueva hora
                    documento.set(hashMapOf("HorasRegistradas" to mutableListOf(fechaHoraFormateada)))
                }
            } else {
                Log.d("Firestore", "Error al obtener el documento : ${task.exception}")
            }
        }

        //obtengo el contexto
        val context = requireContext()

        val drawable = context.getDrawable(R.drawable.boton_hora_registro)
        val drawable2 = context.getDrawable(R.drawable.boton_hora_salida)

      //cambiamos el boton de color
        binding.button3.background = drawable
        binding.button2.background = drawable2

        binding.button2.isEnabled = false
        binding.button3.isEnabled = true

    }

    private fun miBotonSalida() {

        //obtenemos la fecha y hora actual
        val datosSalida = LocalDateTime.now()

        //convierte la fecha y hora en un formato legible
        val formatoFechaHora = DateTimeFormatter.ofPattern("dd-MM-yy / HH:mm")
        val fechaHoraFormateada = datosSalida.format(formatoFechaHora)

        val email = firebaseAuth.currentUser?.email
        val db = FirebaseFirestore.getInstance()


        val documento = db.collection("salidas").document(email.toString())


        //obtiene el documento existente
        documento.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documentExiste = task.result
                if (documentExiste.exists()) {
                    //obtiene campo HorasRegistradas"
                    val horasRegistradas =
                        documentExiste.get("HorasRegistradas") as? MutableList<String>
                            ?: mutableListOf()

                    //agrega la nueva hora al final de la lista
                    horasRegistradas.add(fechaHoraFormateada)

                    //actualiza el campo "HorasRegistradas"
                    documento.update("HorasRegistradas", horasRegistradas)
                } else {
                    //crea documento con la nueva hora
                    documento.set(hashMapOf("HorasRegistradas" to mutableListOf(fechaHoraFormateada)))
                }
            } else {
                Log.d("Firestore", "Error al obtener el documento : ${task.exception}")
            }
        }

        //al cambiar este valor, desbloqueamos el botón de entrada
        entradaActiva = true

        //cada vez que arranque esta función se bloque el botón y se desbloquea el otro
        binding.button3.isEnabled = false
        binding.button2.isEnabled = true

        //obtengo el contexto
        val context = requireContext()

        val drawable = context.getDrawable(R.drawable.boton_hora_registro)
        val drawable2 = context.getDrawable(R.drawable.boton_hora_salida)

        //se cambia el color del botón
        binding.button3.background = drawable2
        binding.button2.background = drawable

        // despues de la lógica actual para registrar la salida, muestra el alertdialog
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Complementarias")
        builder.setMessage("¿Cuántas complementarias has realizado?")

        //agrega editText para que se añadan las horas complementarias
        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(input)

        builder.setPositiveButton("Guardar") { _, _ ->
            val horasComplementariasStr = input.text.toString()
            if (horasComplementariasStr.isNotEmpty()) {
                //convierte el texto en número entero
                val horasComplementarias = horasComplementariasStr.toInt()

                //guarda las horas complementarias en la base de datos
                guardarHorasComplementarias(horasComplementarias)

            } else {
                //el usuario no ingresón un número válido
                Toast.makeText(
                    requireContext(),
                    "Por favor , ingresa un número válido",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        builder.setNegativeButton("No") { _, _ ->
            //selecciona no habe realizado horas compl.

        }
        builder.show()
    }


    private fun guardarHorasComplementarias(horasComplementarias: Int) {
        // obtén la dirección de correo del usuario
        val email = firebaseAuth.currentUser?.email
        val db = FirebaseFirestore.getInstance()

        // crear o acceder a la referencia de la base de datos de las horas compl.
        val complementariasRef = db.collection("complementarias").document(email.toString())

        //obtener fecha actual
        val currentDate = LocalDateTime.now()
        val formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(currentDate)

        //verifica si existe el documento
        complementariasRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document.exists()) {
                    //si existe actualiza el campo "horasComplementarias"
                    val horasComplementariasData =
                        document.get("horasComplementarias") as? MutableMap<String, Int>
                            ?: mutableMapOf()

                    //agrega las horas complementarias para la fecha actual
                    horasComplementariasData[formattedDate] = horasComplementarias

                    //Actualiza el campo "horasComplementarias"
                    complementariasRef.update("horasComplementarias", horasComplementariasData)
                        .addOnCompleteListener {
                            //el usuario no ingresón un número válido
                            Toast.makeText(
                                requireContext(),
                                "Horas complementarias guardadas correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener {
                            Log.d("Firestore", "Error al guardar las horas complementarias: $it")
                        }

                }
            }
        }
    }
}









