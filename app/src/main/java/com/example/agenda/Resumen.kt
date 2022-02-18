package com.example.agenda

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import java.io.IOException
import java.io.OutputStreamWriter

class Resumen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumen)
        //se crea una instancia del intent
        val objetoIntent: Intent = intent
        // se crea una instancia del TextView resumen
        val resumen: TextView = findViewById(R.id.resumen)
        // se asigna el textto obtenido del intent a la variable resumen
        resumen.text = HtmlCompat.fromHtml(
            objetoIntent.getStringExtra("resumen").toString(),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        // se crea una instancia del Button cancelar_guradado
        val cancelar: Button = findViewById(R.id.cancelar_guradado)
        // se asigna al evento click la navegación a la vista principal
        cancelar.setOnClickListener {
            startActivity(Intent(this, Form::class.java))
        }
        // se crea una instancia del Button guardar
        val guardar: Button = findViewById(R.id.guardar)
        // se asigna al evento click la funcion de guardado del evento y redireccion a la vista principal
        guardar.setOnClickListener {
            try {
                val archivo =
                    OutputStreamWriter(openFileOutput("eventos.txt", Activity.MODE_APPEND))
                archivo.append(objetoIntent.getStringExtra("resumen"))
                archivo.flush()
                archivo.close()
                Log.i("Exitoso", "El evento fue guardado de forma exitosa")
            } catch (e: IOException) {
                Log.e("Error en guardado", e.toString())
            }
            Toast.makeText(this, "El evento fue guardado de forma correcta", Toast.LENGTH_SHORT)
                .show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}