package com.example.appregistrohorasproyecto

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.appregistrohorasproyecto.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
            toggle = ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            val fragmentManager = supportFragmentManager

            val navView = findViewById<NavigationView>(R.id.nav_view)

            navView.setNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_home -> {
                        val newFragment =
                          FragmentHome() // Reemplaza con el nombre de tu fragmento
                        fragmentManager.beginTransaction()
                            .replace(R.id.contenedor, newFragment)
                            .commit()
                    }

                    R.id.nav_datos-> {
                        val newFragment =
                            VerPerfinFragment() // Reemplaza con el nombre de tu fragmento
                        fragmentManager.beginTransaction()
                            .replace(R.id.contenedor, newFragment)
                            .commit()
                    }

                    R.id.modDatos -> {
                        val newFragment =
                            modPerflFragment() // Reemplaza con el nombre de tu fragmento
                        fragmentManager.beginTransaction()
                            .replace(R.id.contenedor, newFragment)
                            .commit()
                    }



                    R.id.nav_horario -> {
                        val newFragment =
                           HorarioFragment()
                        fragmentManager.beginTransaction()
                            .replace(R.id.contenedor, newFragment)
                            .commit()
                    }
                    R.id.nav_nomina -> {
                        val newFragment =
                            FragmentNominas() // Reemplaza con el nombre de tu fragmento
                        fragmentManager.beginTransaction()
                            .replace(R.id.contenedor, newFragment)
                            .commit()
                    }

                    R.id.simul_nomina -> {
                        val newFragment =
                            SimuladorFragment() // Reemplaza con el nombre de tu fragmento
                        fragmentManager.beginTransaction()
                            .replace(R.id.contenedor, newFragment)
                            .commit()
                    }


                    R.id.nav_ver_registro_horas -> {
                        val newFragment =
                           Ver_horas()
                        fragmentManager.beginTransaction()
                            .replace(R.id.contenedor, newFragment)
                            .commit()
                    }




                    R.id.cerrar_sesion -> {
                        FirebaseAuth.getInstance().signOut()
                        val intent= Intent(this, AuthActivity::class.java)
                        startActivity(intent)
                    }
                }
                true
            }

        //llamo al FragmentHome porque la principal función es registrar el horario
        val fragment = FragmentHome()

        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedor, fragment)
            .commit()

        }



        fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
            val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {

            if (toggle.onOptionsItemSelected(item)) {
                return true
            }
            return super.onOptionsItemSelected(item)

        }


    }
















