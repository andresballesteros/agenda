package com.example.agenda

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Space
import android.widget.Toast
import java.io.IOException
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Se crea una instancia del boton aceptar
        val botonAceptar: Button = findViewById(R.id.boton_aceptar)
        //Se agrega la funcionalidad de pasar a la activity Form (cambia de vista o pantalla)
        botonAceptar.setOnClickListener {
            startActivity(Intent(this, Form::class.java))
        }
        //Crea el archivo donde se guardaran los eventods
        crearArchivoEventos()
        // Se crea una instancia del boton ver eventtos
        val botonVerEventos: Button = findViewById(R.id.ver_eventos)
        //Se agrega la funcionalidad de pasar a la activity Guardados (cambia de vista o pantalla)
        botonVerEventos.setOnClickListener { startActivity(Intent(this, Guardados::class.java)) }
    }

    //Funcion que crea el archivo txt donde se guardarán los eventos
    private fun crearArchivoEventos() {
        try {
            val archivo = OutputStreamWriter(openFileOutput("eventos.txt", Activity.MODE_APPEND))
            archivo.flush()
            archivo.close()
        } catch (e: IOException) {

        }
    }

}