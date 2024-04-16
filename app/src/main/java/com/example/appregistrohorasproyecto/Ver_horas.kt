package com.example.appregistrohorasproyecto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.appregistrohorasproyecto.databinding.FragmentVerHorasBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar


class Ver_horas : Fragment() {

    private lateinit var binding: FragmentVerHorasBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflar el diseño del fragmento
        binding = FragmentVerHorasBinding.inflate(inflater, container, false)
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
        val spinner1: Spinner = view.findViewById(R.id.spinnerYearVerHoras)
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val years = (currentYear downTo currentYear - 10).map { it.toString() }
        val yearAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = yearAdapter

        // Rellenar Spinner de Mes
        val spinner2: Spinner = view.findViewById(R.id.spinnerMonthVerHoras)
        val months = arrayOf(
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        )
        val monthAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, months)
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = monthAdapter

        // Listener para el botón de calcular
        val verButton = binding.buttonVer
        verButton.setOnClickListener {
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
                    binding.textdiashoras.text = "Error al obtener registros de entradas: $exception"
                }
                .addOnCompleteListener {
                    // Al completar la obtención de entradas, calcular las horas trabajadas
                    val horasPorDia = calcularHorasPorDiaEnMes(horasEntradas, horasSalidas, selectedYear, selectedMonth)
                    mostrarHorasPorDiaEnTextView(horasPorDia)
                }

            // Obtener los datos de salidas
            salidasRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val horasSalidasData = documentSnapshot.get("HorasRegistradas") as? List<String>
                        horasSalidas = horasSalidasData ?: emptyList()
                    }
                }
                .addOnFailureListener { exception ->
                    // Manejar errores
                    binding.textdiashoras.text = "Error al obtener registros de salidas: $exception"
                }
                .addOnCompleteListener {
                    // Al completar la obtención de salidas, calcular las horas trabajadas
                    val horasPorDia = calcularHorasPorDiaEnMes(horasEntradas, horasSalidas, selectedYear, selectedMonth)
                    mostrarHorasPorDiaEnTextView(horasPorDia)
                }
        }

        return view
    }

    // Función para calcular las horas trabajadas por día en el mes actual
    private fun calcularHorasPorDiaEnMes(horasEntradas: List<String>, horasSalidas: List<String>, selectedYear: String, selectedMonth: Int): Map<LocalDate, Double> {
        val ahora = LocalDateTime.now()
        val mesActual = ahora.monthValue

        val horasPorDia = mutableMapOf<LocalDate, Double>()

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
                        val fechaDia = fechaEntrada.toLocalDate()

                        horasPorDia[fechaDia] = horasPorDia.getOrDefault(fechaDia, 0.0) + duracion.toHours().toDouble()
                    }
                } catch (e: DateTimeParseException) {
                    println("Error al analizar la fecha y hora: ${e.message}")
                }
            }
        } else {
            println("Error: Las listas de entradas y salidas no tienen la misma longitud.")
        }

        return horasPorDia
    }

    private fun mostrarHorasPorDiaEnTextView(horasPorDia: Map<LocalDate, Double>) {
        // Utilizar un StringBuilder para construir el contenido del TextView
        val contentStringBuilder = StringBuilder()

        // Construir el contenido del TextView
        contentStringBuilder.append("Horas trabajadas este mes por día:\n")

        for ((fechaDia, horas) in horasPorDia) {
            val formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val fechaFormateada = formatoFecha.format(fechaDia)
            contentStringBuilder.append("$fechaFormateada: $horas horas\n")
        }

        // Mostrar el contenido en el TextView utilizando binding
        binding.textdiashoras.text = contentStringBuilder.toString()

        // Configurar desplazamiento en el ScrollView
        binding.scrollViewVerHoras.post { binding.scrollViewVerHoras.fullScroll(View.FOCUS_DOWN) }
    }
}
