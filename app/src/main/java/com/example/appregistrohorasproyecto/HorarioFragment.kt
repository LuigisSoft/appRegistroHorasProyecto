package com.example.appregistrohorasproyecto

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.appregistrohorasproyecto.databinding.FragmentHorarioBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.TimeZone


class HorarioFragment : Fragment() {

    private lateinit var binding: FragmentHorarioBinding
    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var db: FirebaseFirestore
    private lateinit var monday1: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragmento utilizando View Binding
        binding = FragmentHorarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


// ...

        val email=firebaseAuth.currentUser?.email

        val monday1 = binding.monday1
        val monday2 = binding.monday2
        val monday3 = binding.monday3
        val monday4 = binding.monday4

        val tuesday1 = binding.tuesday1
        val tuesday2 = binding.tuesday2
        val tuesday3 = binding.tuesday3
        val tuesday4 = binding.tuesday4

        val wednesday1 = binding.wednesday1
        val wednesday2 = binding.wednesday2
        val wednesday3 = binding.wednesday3
        val wednesday4 = binding.wednesday4

        val thursday1 = binding.thursday1
        val thursday2 = binding.thursday2
        val thursday3 = binding.thursday3
        val thursday4 = binding.thursday4

        val friday1 = binding.friday1
        val friday2 = binding.friday2
        val friday3= binding.friday3
        val friday4= binding.friday4

        val saturday1 = binding.saturday1
        val saturday2 = binding.saturday2
        val saturday3= binding.saturday3
        val saturday4= binding.saturday4

        val sunday1 = binding.sunday1
        val sunday2 = binding.sunday2
        val sunday3= binding.sunday3
        val sunday4= binding.sunday4



        val db = FirebaseFirestore.getInstance()

        val timezone = TimeZone.getTimeZone("Europe/Madrid")

        // Establecer la zona horaria del dispositivo
        TimeZone.setDefault(timezone)

        binding.btnSemana1.setOnClickListener{
            db.collection("horario").document(email.toString())
                .get()
                .addOnSuccessListener {

                    //turno lunes mañana--------------------------------------------------------------------------------
                    val monday1Timestamp=it.get("lunes turno1") as Timestamp?

                    //comprueba si el valor es nulo
                    if (monday1Timestamp == null){
                        //indicara que no se trabaja, día libre
                        monday1.text="Libre"

                    }else{
                        val monday1HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(monday1Timestamp.toDate())
                        monday1.text="Entrada:  $monday1HumanReadable"
                    }

                    val monday2Timestamp=it.get("lunes turno1 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (monday2Timestamp == null){
                        //indicara que no se trabaja, día libre
                        monday3.text="Libre"
                    }else{
                        val monday2HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(monday2Timestamp.toDate())
                        monday3.text="Salida:  $monday2HumanReadable"
                    }

                    //-------------------------------------------------------------------------------------------------
                    //turno lunes noche
                    val monday3Timestamp = it.get("lunes turno2") as Timestamp?

                    // Comprobar si el valor es nulo
                    if (monday3Timestamp == null) {
                        // Mostrar un mensaje de error
                        monday2.text = "Libre"
                    } else {
                        // Convertir el timestamp a LocalDateTime en la zona horaria del dispositivo
                        val monday3HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(monday3Timestamp.toDate())
                        monday2.text ="Entrada :  $monday3HumanReadable"
                    }
                    val monday4Timestamp = it.get("lunes turno2 salida") as Timestamp?

                    // Comprobar si el valor es nulo
                    if (monday4Timestamp == null) {
                        // Mostrar un mensaje de error
                        monday4.text = "Libre"
                    } else {
                        // Convertir el timestamp a LocalDateTime en la zona horaria del dispositivo
                        val monday4HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(monday4Timestamp.toDate())
                        monday4.text ="Salida :  $monday4HumanReadable"
                    }

                    //turno martes mañana--------------------------------------------------------------------------------
                    val tuesday1Timestamp=it.get("martes turno1") as Timestamp?

                    //comprueba si el valor es nulo
                    if (tuesday1Timestamp == null){
                        //indicara que no se trabaja, día libre
                        tuesday1.text="Libre"

                    }else{
                        val tuesday1HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(tuesday1Timestamp.toDate())
                        tuesday1.text="Entrada:  $tuesday1HumanReadable"
                    }

                    val tuesday2Timestamp=it.get("martes turno1 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (tuesday2Timestamp == null){
                        //indicara que no se trabaja, día libre
                        tuesday3.text="Libre"
                    }else{
                        val tuesday2HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(tuesday2Timestamp.toDate())
                        tuesday3.text="Salida:  $tuesday2HumanReadable"
                    }
                    //----turno martes noche---------------------------------------------------------
                    val tuesday3Timestamp = it.get("martes turno2") as Timestamp?

                    // Comprobar si el valor es nulo
                    if (tuesday3Timestamp == null) {
                        // Mostrar un mensaje de error
                        tuesday2.text = "Libre"
                    } else {
                        // Convertir el timestamp a LocalDateTime en la zona horaria del dispositivo
                        val tuesday3HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(tuesday3Timestamp.toDate())
                        tuesday2.text = "Entrada :  $tuesday3HumanReadable"
                    }
                    val tuesday4Timestamp = it.get("martes turno2 salida") as Timestamp?

                    // Comprobar si el valor es nulo
                    if (tuesday4Timestamp == null) {
                        // Mostrar un mensaje de error
                        tuesday4.text = "Libre"
                    } else {
                        // Convertir el timestamp a LocalDateTime en la zona horaria del dispositivo
                        val tuesday4HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(tuesday4Timestamp.toDate())
                        tuesday4.text = "Salida :  $tuesday4HumanReadable"
                    }
                    //----turno miercoles turno1---------------------------------------------------------

                    val wednesday1Timestamp=it.get("miercoles turno1") as Timestamp?

                    //comprueba si el valor es nulo
                    if (wednesday1Timestamp == null){
                        //indicara que no se trabaja, día libre
                        wednesday1.text="Libre"

                    }else{
                        val wednesday1HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(wednesday1Timestamp.toDate())
                        wednesday1.text="Entrada: $wednesday1HumanReadable"
                    }

                    val wednesday2Timestamp=it.get("miercoles turno1 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (wednesday2Timestamp == null){
                        //indicara que no se trabaja, día libre
                        wednesday3.text="Libre"
                    }else{
                        val wednesday2HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(wednesday2Timestamp.toDate())
                        wednesday3.text="Salida:  $wednesday2HumanReadable"
                    }

                    //-----------miercoles turno2----------------------------------------------------------

                    val wednesday3Timestamp=it.get("miercoles turno2") as Timestamp?

                    //comprueba si el valor es nulo
                    if (wednesday3Timestamp == null){
                        //indicara que no se trabaja, día libre
                        wednesday2.text="Libre"

                    }else{
                        val wednesday1HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(wednesday3Timestamp.toDate())
                        wednesday2.text="Entrada:  $wednesday1HumanReadable"
                    }

                    val wednesday4Timestamp=it.get("miercoles turno2 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (wednesday4Timestamp == null){
                        //indicara que no se trabaja, día libre
                        wednesday4.text="Libre"
                    }else{
                        val wednesday2HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(wednesday4Timestamp.toDate())
                        wednesday4.text="Salida:  $wednesday2HumanReadable"
                    }



                    //----turno jueves ---------------------------------------------------------

                    val thursday1Timestamp=it.get("jueves turno1") as Timestamp?

                    //comprueba si el valor es nulo
                    if (thursday1Timestamp == null){
                        //indicara que no se trabaja, día libre
                        thursday1.text="Libre"
                    }else{
                        val thursday1HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(thursday1Timestamp.toDate())
                        thursday1.text="Entrada:  $thursday1HumanReadable"
                    }

                    val thursday2Timestamp=it.get("jueves turno1 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (thursday2Timestamp == null){
                        //indicara que no se trabaja, día libre
                        thursday3.text="Libre"
                    }else{
                        val thursday2HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(thursday2Timestamp.toDate())
                        thursday3.text="Salida:  $thursday2HumanReadable"
                    }
                    //----turno jueves noche---------------------------------------------------------
                    val thursday3Timestamp=it.get("jueves turno2") as Timestamp?

                    //comprueba si el valor es nulo
                    if (thursday3Timestamp == null){
                        //indicara que no se trabaja, día libre
                        thursday2.text="Libre"

                    }else{
                        val thursday3HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(thursday3Timestamp.toDate())
                        thursday2.text="Entrada:  $thursday3HumanReadable"
                    }

                    val thursday4Timestamp=it.get("jueves turno2 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (thursday4Timestamp == null){
                        //indicara que no se trabaja, día libre
                        thursday4.text="Libre"
                    }else{
                        val thursday4HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(thursday4Timestamp.toDate())
                        thursday4.text="Salida:  $thursday4HumanReadable"
                    }

                    //----------------turno viernes----------------------------------------------------------------------------
                    val friday1Timestamp=it.get("viernes turno1") as Timestamp?

                    //comprueba si el valor es nulo
                    if (friday1Timestamp == null){
                        //indicara que no se trabaja, día libre
                        friday1.text="Libre"
                    }else{
                        val friday1HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(friday1Timestamp.toDate())
                        friday1.text="Entrada:  $friday1HumanReadable"
                    }

                    val friday2Timestamp=it.get("viernes turno1 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (friday2Timestamp == null){
                        //indicara que no se trabaja, día libre
                        friday2.text="Libre"
                    }else{
                        val thursday2HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(friday2Timestamp.toDate())
                        friday2.text="Salida:  $thursday2HumanReadable"
                    }

                    //--------turno viernes noche-------------------------------------------------------------------
                    val friday3Timestamp=it.get("viernes turno2") as Timestamp?

                    //comprueba si el valor es nulo
                    if (friday3Timestamp == null){
                        //indicara que no se trabaja, día libre
                        friday3.text="Libre"
                    }else{
                        val friday3HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(friday3Timestamp.toDate())
                        friday3.text="Entrada:  $friday3HumanReadable"
                    }

                    val friday4Timestamp=it.get("viernes turno2 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (friday4Timestamp == null){
                        //indicara que no se trabaja, día libre
                        friday4.text="Libre"
                    }else{
                        val friday4HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(friday4Timestamp.toDate())
                        friday4.text="Salida:  $friday4HumanReadable"
                    }
                    //---------turno sabado-----------------------------------------------------------------------
                    val saturday1Timestamp=it.get("sabado turno1") as Timestamp?

                    //comprueba si el valor es nulo
                    if (saturday1Timestamp == null){
                        //indicara que no se trabaja, día libre
                        saturday1.text="Libre"
                    }else{
                        val saturday1HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(saturday1Timestamp.toDate())
                        saturday1.text="Entrada:  $saturday1HumanReadable"
                    }

                    val saturday2Timestamp=it.get("sabado turno1 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (saturday2Timestamp == null){
                        //indicara que no se trabaja, día libre
                        saturday2.text="Libre"
                    }else{
                        val saturday2HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(saturday2Timestamp.toDate())
                        saturday2.text="Salida:  $saturday2HumanReadable"
                    }
                    //----------------turno sabado noche---------------------------------------------------------

                    val saturday3Timestamp=it.get("sabado turno2") as Timestamp?

                    //comprueba si el valor es nulo
                    if (saturday3Timestamp == null){
                        //indicara que no se trabaja, día libre
                        saturday3.text="Libre"
                    }else{
                        val saturday1HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(saturday3Timestamp.toDate())
                        saturday3.text="Entrada:  $saturday1HumanReadable"
                    }

                    val saturday4Timestamp=it.get("sabado turno2 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (saturday4Timestamp == null){
                        //indicara que no se trabaja, día libre
                        saturday4.text="Libre"
                    }else{
                        val saturday4HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(saturday4Timestamp.toDate())
                        saturday4.text="Salida:  $saturday4HumanReadable"
                    }
                    //---------turno domingo-----------------------------------------------------------------------
                    val sunday1Timestamp=it.get("domingo turno1") as Timestamp?

                    //comprueba si el valor es nulo
                    if (sunday1Timestamp == null){
                        //indicara que no se trabaja, día libre
                        sunday1.text="Libre"
                    }else{
                        val sunday1HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(sunday1Timestamp.toDate())
                        sunday1.text="Entrada:  $sunday1HumanReadable"
                    }

                    val sunday2HumanReadable=it.get("domingo turno1 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (sunday2HumanReadable == null){
                        //indicara que no se trabaja, día libre
                        sunday2.text="Libre"
                    }else{
                        val sunday2HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(sunday2HumanReadable.toDate())
                        sunday2.text="Salida:  $sunday2HumanReadable"
                    }
                    //----------------turno sabado noche---------------------------------------------------------

                    val sunday3HumanReadable=it.get("domingo turno2") as Timestamp?

                    //comprueba si el valor es nulo
                    if (sunday3HumanReadable == null){
                        //indicara que no se trabaja, día libre
                        sunday3.text="Libre"
                    }else{
                        val sunday3HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(sunday3HumanReadable.toDate())
                        sunday3.text="Entrada:  $sunday3HumanReadable"
                    }

                    val sunday4Timestamp=it.get("domingo turno2 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (sunday4Timestamp == null){
                        //indicara que no se trabaja, día libre
                        sunday4.text="Libre"
                    }else{
                        val sunday4HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(sunday4Timestamp.toDate())
                        sunday4.text="Salida:  $sunday4HumanReadable"
                    }



                }


        }

        binding.btnSemana2.setOnClickListener{
            db.collection("horario2").document(email.toString())
                .get()
                .addOnSuccessListener {

                    //turno lunes mañana--------------------------------------------------------------------------------
                    val monday1Timestamp=it.get("lunes turno1") as Timestamp?

                    //comprueba si el valor es nulo
                    if (monday1Timestamp == null){
                        //indicara que no se trabaja, día libre
                        monday1.text="Libre"

                    }else{
                        val monday1HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(monday1Timestamp.toDate())
                        monday1.text="Entrada:  $monday1HumanReadable"
                    }

                    val monday2Timestamp=it.get("lunes turno1 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (monday2Timestamp == null){
                        //indicara que no se trabaja, día libre
                        monday3.text="Libre"
                    }else{
                        val monday2HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(monday2Timestamp.toDate())
                        monday3.text="Salida:   $monday2HumanReadable"
                    }

                    //-------------------------------------------------------------------------------------------------
                    //turno lunes noche
                    val monday3Timestamp = it.get("lunes turno2") as Timestamp?

                    // Comprobar si el valor es nulo
                    if (monday3Timestamp == null) {
                        // Mostrar un mensaje de error
                        monday2.text = "Libre"
                    } else {
                        // Convertir el timestamp a LocalDateTime en la zona horaria del dispositivo
                        val monday3HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(monday3Timestamp.toDate())
                        monday2.text = "Entrada :  $monday3HumanReadable"
                    }
                    val monday4Timestamp = it.get("lunes turno2 salida") as Timestamp?

                    // Comprobar si el valor es nulo
                    if (monday4Timestamp == null) {
                        // Mostrar un mensaje de error
                        monday4.text = "Libre"
                    } else {
                        // Convertir el timestamp a LocalDateTime en la zona horaria del dispositivo
                        val monday4HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(monday4Timestamp.toDate())
                        monday4.text = "Salida :  $monday4HumanReadable"
                    }

                    //turno martes mañana--------------------------------------------------------------------------------
                    val tuesday1Timestamp=it.get("martes turno1") as Timestamp?

                    //comprueba si el valor es nulo
                    if (tuesday1Timestamp == null){
                        //indicara que no se trabaja, día libre
                        tuesday1.text="Libre"

                    }else{
                        val tuesday1HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(tuesday1Timestamp.toDate())
                        tuesday1.text="Entrada:  $tuesday1HumanReadable"
                    }

                    val tuesday2Timestamp=it.get("martes turno1 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (tuesday2Timestamp == null){
                        //indicara que no se trabaja, día libre
                        tuesday3.text="Libre"
                    }else{
                        val tuesday2HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(tuesday2Timestamp.toDate())
                        tuesday3.text="Salida:  $tuesday2HumanReadable"
                    }
                    //----turno martes noche---------------------------------------------------------
                    val tuesday3Timestamp = it.get("martes turno2") as Timestamp?

                    // Comprobar si el valor es nulo
                    if (tuesday3Timestamp == null) {
                        // Mostrar un mensaje de error
                        tuesday2.text = "Libre"
                    } else {
                        // Convertir el timestamp a LocalDateTime en la zona horaria del dispositivo
                        val tuesday3HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(tuesday3Timestamp.toDate())
                        tuesday2.text = "Entrada :  $tuesday3HumanReadable"
                    }
                    val tuesday4Timestamp = it.get("martes turno2 salida") as Timestamp?

                    // Comprobar si el valor es nulo
                    if (tuesday4Timestamp == null) {
                        // Mostrar un mensaje de error
                        tuesday4.text = "Libre"
                    } else {
                        // Convertir el timestamp a LocalDateTime en la zona horaria del dispositivo
                        val tuesday4HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(tuesday4Timestamp.toDate())
                        tuesday4.text = "Salida :  $tuesday4HumanReadable"
                    }
                    //----turno miercoles turno1---------------------------------------------------------

                    val wednesday1Timestamp=it.get("miercoles turno1") as Timestamp?

                    //comprueba si el valor es nulo
                    if (wednesday1Timestamp == null){
                        //indicara que no se trabaja, día libre
                        wednesday1.text="Libre"

                    }else{
                        val wednesday1HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(wednesday1Timestamp.toDate())
                        wednesday1.text="Entrada:  $wednesday1HumanReadable"
                    }

                    val wednesday2Timestamp=it.get("miercoles turno1 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (wednesday2Timestamp == null){
                        //indicara que no se trabaja, día libre
                        wednesday3.text="Libre"
                    }else{
                        val wednesday2HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(wednesday2Timestamp.toDate())
                        wednesday3.text="Salida:  $wednesday2HumanReadable"
                    }

                    //-----------miercoles turno2----------------------------------------------------------

                    val wednesday3Timestamp=it.get("miercoles turno2") as Timestamp?

                    //comprueba si el valor es nulo
                    if (wednesday3Timestamp == null){
                        //indicara que no se trabaja, día libre
                        wednesday2.text="Libre"

                    }else{
                        val wednesday1HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(wednesday3Timestamp.toDate())
                        wednesday2.text="Entrada:  $wednesday1HumanReadable"
                    }

                    val wednesday4Timestamp=it.get("miercoles turno2 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (wednesday4Timestamp == null){
                        //indicara que no se trabaja, día libre
                        wednesday4.text="Libre"
                    }else{
                        val wednesday2HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(wednesday4Timestamp.toDate())
                        wednesday4.text="Salida:  $wednesday2HumanReadable"
                    }



                    //----turno jueves ---------------------------------------------------------

                    val thursday1Timestamp=it.get("jueves turno1") as Timestamp?

                    //comprueba si el valor es nulo
                    if (thursday1Timestamp == null){
                        //indicara que no se trabaja, día libre
                        thursday1.text="Libre"
                    }else{
                        val thursday1HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(thursday1Timestamp.toDate())
                        thursday1.text="Entrada:  $thursday1HumanReadable"
                    }

                    val thursday2Timestamp=it.get("jueves turno1 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (thursday2Timestamp == null){
                        //indicara que no se trabaja, día libre
                        thursday3.text="Libre"
                    }else{
                        val thursday2HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(thursday2Timestamp.toDate())
                        thursday3.text="Salida:  $thursday2HumanReadable"
                    }
                    //----turno jueves noche---------------------------------------------------------
                    val thursday3Timestamp=it.get("jueves turno2") as Timestamp?

                    //comprueba si el valor es nulo
                    if (thursday3Timestamp == null){
                        //indicara que no se trabaja, día libre
                        thursday2.text="Libre"

                    }else{
                        val thursday3HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(thursday3Timestamp.toDate())
                        thursday2.text="Entrada:  $thursday3HumanReadable"
                    }

                    val thursday4Timestamp=it.get("jueves turno2 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (thursday4Timestamp == null){
                        //indicara que no se trabaja, día libre
                        thursday4.text="Libre"
                    }else{
                        val thursday4HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(thursday4Timestamp.toDate())
                        thursday4.text="Salida:  $thursday4HumanReadable"
                    }

                    //----------------turno viernes----------------------------------------------------------------------------
                    val friday1Timestamp=it.get("viernes turno1") as Timestamp?

                    //comprueba si el valor es nulo
                    if (friday1Timestamp == null){
                        //indicara que no se trabaja, día libre
                        friday1.text="Libre"
                    }else{
                        val friday1HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(friday1Timestamp.toDate())
                        friday1.text="Entrada:  $friday1HumanReadable"
                    }

                    val friday2Timestamp=it.get("viernes turno1 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (friday2Timestamp == null){
                        //indicara que no se trabaja, día libre
                        friday2.text="Libre"
                    }else{
                        val thursday2HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(friday2Timestamp.toDate())
                        friday2.text="Salida:  $thursday2HumanReadable"
                    }

                    //--------turno viernes noche-------------------------------------------------------------------
                    val friday3Timestamp=it.get("viernes turno2") as Timestamp?

                    //comprueba si el valor es nulo
                    if (friday3Timestamp == null){
                        //indicara que no se trabaja, día libre
                        friday3.text="Libre"
                    }else{
                        val friday3HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(friday3Timestamp.toDate())
                        friday3.text="Entrada:  $friday3HumanReadable"
                    }

                    val friday4Timestamp=it.get("viernes turno2 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (friday4Timestamp == null){
                        //indicara que no se trabaja, día libre
                        friday4.text="Libre"
                    }else{
                        val friday4HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(friday4Timestamp.toDate())
                        friday4.text="Salida:  $friday4HumanReadable"
                    }
                    //---------turno sabado-----------------------------------------------------------------------
                    val saturday1Timestamp=it.get("sabado turno1") as Timestamp?

                    //comprueba si el valor es nulo
                    if (saturday1Timestamp == null){
                        //indicara que no se trabaja, día libre
                        saturday1.text="Libre"
                    }else{
                        val saturday1HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(saturday1Timestamp.toDate())
                        saturday1.text="Entrada:  $saturday1HumanReadable"
                    }

                    val saturday2Timestamp=it.get("sabado turno1 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (saturday2Timestamp == null){
                        //indicara que no se trabaja, día libre
                        saturday2.text="Libre"
                    }else{
                        val saturday2HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(saturday2Timestamp.toDate())
                        saturday2.text="Salida:  $saturday2HumanReadable"
                    }
                    //----------------turno sabado noche---------------------------------------------------------

                    val saturday3Timestamp=it.get("sabado turno2") as Timestamp?

                    //comprueba si el valor es nulo
                    if (saturday3Timestamp == null){
                        //indicara que no se trabaja, día libre
                        saturday3.text="Libre"
                    }else{
                        val saturday1HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(saturday3Timestamp.toDate())
                        saturday3.text="Entrada:  $saturday1HumanReadable"
                    }

                    val saturday4Timestamp=it.get("sabado turno2 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (saturday4Timestamp == null){
                        //indicara que no se trabaja, día libre
                        saturday4.text="Libre"
                    }else{
                        val saturday4HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(saturday4Timestamp.toDate())
                        saturday4.text="Salida:  $saturday4HumanReadable"
                    }
                    //---------turno domingo-----------------------------------------------------------------------
                    val sunday1Timestamp=it.get("domingo turno1") as Timestamp?

                    //comprueba si el valor es nulo
                    if (sunday1Timestamp == null){
                        //indicara que no se trabaja, día libre
                        sunday1.text="Libre"
                    }else{
                        val sunday1HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(sunday1Timestamp.toDate())
                        sunday1.text="Entrada:  $sunday1HumanReadable"
                    }

                    val sunday2HumanReadable=it.get("domingo turno1 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (sunday2HumanReadable == null){
                        //indicara que no se trabaja, día libre
                        sunday2.text="Libre"
                    }else{
                        val sunday2HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(sunday2HumanReadable.toDate())
                        sunday2.text="Salida:  $sunday2HumanReadable"
                    }
                    //----------------turno sabado noche---------------------------------------------------------

                    val sunday3HumanReadable=it.get("domingo turno2") as Timestamp?

                    //comprueba si el valor es nulo
                    if (sunday3HumanReadable == null){
                        //indicara que no se trabaja, día libre
                        sunday3.text="Libre"
                    }else{
                        val sunday3HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(sunday3HumanReadable.toDate())
                        sunday3.text="Entrada:  $sunday3HumanReadable"
                    }

                    val sunday4Timestamp=it.get("domingo turno2 salida") as Timestamp?

                    //comprueba si el valor es nulo
                    if (sunday4Timestamp == null){
                        //indicara que no se trabaja, día libre
                        sunday4.text="Libre"
                    }else{
                        val sunday4HumanReadable = SimpleDateFormat("dd/MM HH:mm").format(sunday4Timestamp.toDate())
                        sunday4.text="Salida:  $sunday4HumanReadable"
                    }



                }


        }

        //fecha y hora actual
        val now=LocalDateTime.now()
        //semana y mes actuales
        val month=now.month

        binding.btnSemana1.text = "Esta semana"
        //semana siguiente


        binding.btnSemana2.text="Siguiente semana"


    }

    private fun horario1(){



    }
}









