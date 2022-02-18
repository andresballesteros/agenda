package com.example.agenda

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import java.io.*
import java.nio.file.Paths

class Guardados : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guardados)

        var guardados: TextView = findViewById(R.id.guardados)
        obtenerAgendas(guardados)
        val botonBorrar: Button = findViewById(R.id.borrar_eventos)
        botonBorrar.setOnClickListener {
            if (borrarEventos("eventos.txt")) {
                Toast.makeText(
                    this,
                    "Los eventos fueron borrados de forma correcta",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                Toast.makeText(
                    this,
                    "Ocurrió un problema al borrar los eventos",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            startActivity(Intent(this, MainActivity::class.java))
        }
        val botonAtras: Button = findViewById(R.id.atras)
        botonAtras.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun obtenerAgendas(guardados: TextView) {
        if (fileList().contains("eventos.txt")) {
            try {
                val archivo = InputStreamReader(openFileInput("eventos.txt"))
                val br = BufferedReader(archivo)
                var linea = br.readLine()
                val todo = StringBuilder()
                while (linea != null) {
                    todo.append(linea + "\n")
                    linea = br.readLine()
                }
                br.close()
                archivo.close()
                guardados.text =
                    HtmlCompat.fromHtml(todo.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
            } catch (e: IOException) {
            }
        }
    }


    fun borrarEventos(fileString: String): Boolean {
        try {
            val archivo = OutputStreamWriter(openFileOutput("eventos.txt", Activity.MODE_PRIVATE))
            archivo.write("")
            archivo.flush()
            archivo.close()
            return true
        } catch (e: IOException) {
            return false
        }

    }
}

