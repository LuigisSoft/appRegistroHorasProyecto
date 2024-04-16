package com.example.appregistrohorasproyecto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.appregistrohorasproyecto.databinding.FragmentSimuladorBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar

class SimuladorFragment : Fragment() {

    private lateinit var binding: FragmentSimuladorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragmento
        binding = FragmentSimuladorBinding.inflate(inflater, container, false)
        val view = binding.root

        // Obtener la referencia al documento del usuario actual en la colección "entradas"
        val usuarioActual = FirebaseAuth.getInstance().currentUser
        val emailUsuario = usuarioActual?.email
        val entradasRef = FirebaseFirestore.getInstance().collection("entradas").document(emailUsuario!!)
        val salidasRef = FirebaseFirestore.getInstance().collection("salidas").document(emailUsuario)

        // Variables para almacenar los datos
        var horasEntradas = emptyList<String>()
        var horasSalidas = emptyList<String>()

        // Rellenar Spinner de Año
        val spinner1: Spinner = view.findViewById(R.id.spinnerYearSimulador)
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val years = (currentYear downTo currentYear - 10).map { it.toString() }
        val yearAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = yearAdapter

        // Rellenar Spinner de Mes
        val spinner2: Spinner = view.findViewById(R.id.spinnerMonthSimulador)
        val months = arrayOf(
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        )
        val monthAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, months)
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = monthAdapter

        // Listener para el botón de calcular
        val calcularButton = binding.buttonCalcular

        calcularButton.setOnClickListener {
            val selectedYear = spinner1.selectedItem.toString()
            val selectedMonth = spinner2.selectedItemPosition + 1 // Indices de los meses empiezan desde 0

            // Obtener los datos de entradas
            entradasRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val horasEntradasData = documentSnapshot.get("HorasRegistradas") as? List<String>
                        horasEntradas = horasEntradasData ?: emptyList()
                    }
                }
                .addOnFailureListener { exception ->
                    // Manejar errores
                    binding.horasTrabajadas.text = "Error al obtener registros de entradas: $exception"
                }
                .addOnCompleteListener {
                    // Al completar la obtención de entradas, calcular las horas trabajadas
                    val horasTrabajadas = calcularHorasTrabajadas(horasEntradas, horasSalidas, selectedYear, selectedMonth)
                    mostrarHorasTrabajadasEnTextView(horasTrabajadas)

                    obtenerHorasComplementarias(selectedYear, selectedMonth)

                }

            // Obtener los datos de salidas
            salidasRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val horasSalidasData = documentSnapshot.get("HorasRegistradas") as? List<String>
                        horasSalidas = horasSalidasData ?: emptyList()

                        // Calcular las horas nocturnas
                        val horasNocturnas = calcularHorasNocturnas(horasSalidas)



                        // Mostrar las horas nocturnas en el TextView
                        binding.horasNocturnas.text = "$horasNocturnas"




                    }
                }
                .addOnFailureListener { exception ->
                    // Manejar errores
                    binding.horasTrabajadas.text = "Error al obtener registros de salidas: $exception"
                }
                .addOnCompleteListener {
                    // Al completar la obtención de salidas, calcular las horas trabajadas
                    val horasTrabajadas = calcularHorasTrabajadas(horasEntradas, horasSalidas, selectedYear, selectedMonth)
                    mostrarHorasTrabajadasEnTextView(horasTrabajadas)

                }
        }

        return view
    }

    // Función para calcular las horas trabajadas en el mes actual
    private fun calcularHorasTrabajadas(horasEntradas: List<String>, horasSalidas: List<String>, selectedYear: String, selectedMonth: Int): Double {
        val ahora = LocalDateTime.now()
        val mesActual = ahora.monthValue

        var totalHoras = 0.0

        if (horasEntradas.size == horasSalidas.size) {
            val formato = DateTimeFormatter.ofPattern("dd-MM-yy/HH:mm")

            for (i in horasEntradas.indices) {
                try {
                    val fechaEntrada = LocalDateTime.parse(horasEntradas[i].replace(" ", ""), formato)
                    val fechaSalida = LocalDateTime.parse(horasSalidas[i].replace(" ", ""), formato)

                    if (fechaEntrada.year.toString() == selectedYear && fechaEntrada.monthValue == selectedMonth
                        && fechaSalida.year.toString() == selectedYear && fechaSalida.monthValue == selectedMonth) {
                        // Ambas entradas y salidas pertenecen al mes y año seleccionado
                        // Calcula la duración del turno y agrega al total por día
                        val duracion = Duration.between(fechaEntrada, fechaSalida)
                        totalHoras += duracion.toHours().toDouble()
                    }
                } catch (e: DateTimeParseException) {
                    println("Error al analizar la fecha y hora: ${e.message}")
                }
            }
        } else {
            println("Error: Las listas de entradas y salidas no tienen la misma longitud.")
        }

        return totalHoras
    }

    // Función para mostrar las horas trabajadas en el TextView
    private fun mostrarHorasTrabajadasEnTextView(totalHoras: Double) {
        // Mostrar el resultado en el TextView utilizando binding
        binding.horasTrabajadas.text = "$totalHoras horas"
    }

    private fun calcularHorasNocturnas(horasSalidas: List<String>): Double {
        val formato = DateTimeFormatter.ofPattern("dd-MM-yy/HH:mm")
        val limiteNocturno = LocalTime.of(22, 0) // Hora a partir de la cual se consideran horas nocturnas

        var totalHorasNocturnas = 0.0

        for (horaSalida in horasSalidas) {
            try {
                val fechaSalida = LocalDateTime.parse(horaSalida.replace(" ", ""), formato)
                val horaSalidaLocalTime = fechaSalida.toLocalTime()

                if (horaSalidaLocalTime.isAfter(limiteNocturno)) {
                    // La salida ocurrió después de las 22:00 horas, se considera hora nocturna
                    val duracionNocturna = Duration.between(limiteNocturno, horaSalidaLocalTime)
                    totalHorasNocturnas += duracionNocturna.toHours().toDouble()
                }
            } catch (e: DateTimeParseException) {
                println("Error al analizar la fecha y hora de salida: ${e.message}")
            }
        }

        return totalHorasNocturnas
    }

    private fun obtenerHorasComplementarias(selectedYear: String, selectedMonth: Int) {
        // Obtén la dirección de correo electrónico del usuario
        val email = FirebaseAuth.getInstance().currentUser?.email

        // Obtiene la instancia de FirebaseFirestore
        val db = FirebaseFirestore.getInstance()

        // Crea la referencia para la colección "complementarias"
        val complementariasRef = db.collection("complementarias").document(email.toString())

        complementariasRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // El documento existe, intenta obtener el campo "horasComplementarias"
                    val horasComplementariasData = documentSnapshot.get("horasComplementarias") as? Map<String, Int>
                    if (horasComplementariasData != null) {
                        // Filtra las horas complementarias según el mes y año seleccionados
                        val horasFiltradas = horasComplementariasData
                            .filter { (fecha, _) ->
                                val date = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                                date.year.toString() == selectedYear && date.monthValue == selectedMonth
                            }

                        // Calcula la suma total de horas para el mes seleccionado
                        val totalHoras = horasFiltradas.values.sum()

                        // Muestra el resultado en el TextView
                        mostrarHorasComplementariasEnTextView(totalHoras)
                    } else {
                        // El campo "horasComplementarias" no existe o no es un mapa
                        mostrarHorasComplementariasEnTextView(0)
                    }
                } else {
                    // El documento no existe
                    mostrarHorasComplementariasEnTextView(0)
                }
            }
            .addOnFailureListener { exception ->
                // Manejar errores
                mostrarHorasComplementariasEnTextView(0)
                println("Error al obtener registros de horas complementarias: $exception")
            }
    }

    // Función para mostrar las horas complementarias en el TextView
    private fun mostrarHorasComplementariasEnTextView(totalHoras: Int) {
        // Mostrar el resultado en el TextView utilizando binding
        binding.horasComplementarias.text = "$totalHoras horas complementarias"
    }





}








