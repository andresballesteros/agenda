package com.example.agenda

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import java.util.*
import java.text.SimpleDateFormat
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter

class Form : AppCompatActivity() {
    //formatos para las fechas y las horas del formulario
    var formate = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    var timeFormat = SimpleDateFormat("hh:mm:00 a", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_form)

        // Se crea una instancia del EditText nombre_evento
        var nombreEvento: EditText = findViewById(R.id.nombre_evento)
        // Se crea una instancia del Switch todo_el_dia
        var todoElDia: Switch = findViewById(R.id.todo_el_dia)
        // se asigna al evento click la función de hideKeyboard()
        todoElDia.setOnClickListener { hideKeyboard() }
        // Se crea una instancia del RadioGroup
        var tipoEventoGroup: RadioGroup = findViewById(R.id.radioGroup)
        // se asigna al evento click la función de hideKeyboard()
        tipoEventoGroup.setOnClickListener { hideKeyboard() }
        // Se crea una instancia del RadioButton cita
        var cita: RadioButton = findViewById(R.id.cita)
        // se asigna al evento click la función de hideKeyboard()
        cita.setOnClickListener { hideKeyboard() }
        // Se crea una instancia del RadioButton anirversario
        var aniversario: RadioButton = findViewById(R.id.anirversario)
        // se asigna al evento click la función de hideKeyboard()
        aniversario.setOnClickListener { hideKeyboard() }
        // Se crea una instancia del RadioButton cuenta_atras
        var cuentaAtras: RadioButton = findViewById(R.id.cuenta_atras)
        // se asigna al evento click la función de hideKeyboard()
        cuentaAtras.setOnClickListener { hideKeyboard() }
        // Se crea una instancia del EditText descripcion
        var descripcion: EditText = findViewById(R.id.descripcion)
        // Se crea una instancia del EditText fecha_inicio
        var fechaInicio: EditText = findViewById(R.id.fecha_inicio)
        // se asigna al evento click la función de hideKeyboard() y la función para visualizar
        // el selector de fecha
        fechaInicio.setOnClickListener {
            hideKeyboard()
            datePickerShow(fechaInicio)
        }
        // Se crea una instancia del EditText hora_inicio
        var horaInicio: EditText = findViewById(R.id.hora_inicio)
        hideKeyboard()
        // se asigna al evento click la función de hideKeyboard() y la función para visualizar
        // el selector de hora
        horaInicio.setOnClickListener {
            timePickerShow(horaInicio)
        }
        // Se crea una instancia del EditText fecha_fin
        var fechaFin: EditText = findViewById(R.id.fecha_fin)
        // se asigna al evento click la función de hideKeyboard() y la función para visualizar
        // el selector de fecha
        fechaFin.setOnClickListener {
            datePickerShow(fechaFin)
        }
        // Se crea una instancia del EditText hora_fin
        var horaFin: EditText = findViewById(R.id.hora_fin)
        // se asigna al evento click la función de hideKeyboard() y la función para visualizar
        // el selector de hora
        horaFin.setOnClickListener {
            timePickerShow(horaFin)
        }
        // Se crea una instancia del EditText hora_fin
        val botonCanelar: Button = findViewById(R.id.boton_cancelar)
        //Se agrega la funcionalidad de pasar a la activity Main (cambia de vista o pantalla)
        botonCanelar.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        // Se crea una instancia del Button boton_guardar
        val botonGuardar: Button = findViewById(R.id.boton_guardar)
        //acion del boton guardar
        botonGuardar.setOnClickListener {
            //validación del formulario
            if (!validarForm(nombreEvento, fechaInicio, horaInicio, fechaFin, horaFin)) {
                //inicio de actividad para navegar al activity_reumen y se pasa a la actividad el resumen del evento
                    // mediante putExtra
                startActivity(
                    Intent(this, Resumen::class.java).putExtra(
                        "resumen",
                        obtenerResumen(
                            nombreEvento,
                            fechaInicio,
                            horaInicio,
                            fechaFin,
                            horaFin,
                            todoElDia,
                            descripcion,
                            tipoEventoGroup,
                            cita,
                            aniversario,
                            cuentaAtras
                        )
                    )
                )
            }

        }


    }

    // funcion que llama el calendario
    private fun datePickerShow(fechaTexto: EditText) {
        val now = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val date = formate.format(selectedDate.time)
                fechaTexto.setText(date)

            },
            now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH),

            )
        datePicker.show()
    }

    // función que llama el selector de hora
    private fun timePickerShow(horaTexto: EditText) {
        val now = Calendar.getInstance()
        val timePicker = TimePickerDialog(
            this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)
                horaTexto.setText(timeFormat.format(selectedTime.time))
            },
            now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false
        )
        timePicker.show()
    }

    // función para validar los campos del formulario
    private fun validarForm(
        nombreEvento: EditText,
        fechaInicio: EditText,
        horaInicio: EditText,
        fechaFin: EditText,
        horaFin: EditText
    ): Boolean {
        var error = false

        if (TextUtils.isEmpty(nombreEvento.text.toString())) {
            nombreEvento.error = "Requerido"
            error = true
        } else {
            nombreEvento.error = null
        }
        if (TextUtils.isEmpty(fechaInicio.text.toString())) {
            fechaInicio.error = "Requerido"
            error = true
        } else {
            fechaInicio.error = null
        }
        if (TextUtils.isEmpty(horaInicio.text.toString())) {
            horaInicio.error = "Requerido"
            error = true
        } else {
            horaInicio.error = null
        }
        if (TextUtils.isEmpty(fechaFin.text.toString())) {
            fechaFin.error = "Requerido"
            error = true
        } else {
            fechaFin.error = null
        }
        if (TextUtils.isEmpty(horaFin.text.toString())) {
            horaFin.error = "Requerido"
            error = true
        } else {
            horaFin.error = null
        }
        if (!fechaInicio.text.isNullOrBlank() && !horaInicio.text.isNullOrBlank() && !fechaFin.text.isNullOrBlank()
            && !horaFin.text.isNullOrBlank() && validarFechas(
                fechaInicio,
                horaInicio,
                fechaFin,
                horaFin
            )
        ) {
            error = true
            Toast.makeText(
                this,
                "La fecha inicial debe ser anterior a la fecha final",
                Toast.LENGTH_SHORT
            ).show()
        }
        return error
    }

    // función para validar que la fecha fin no sea anterior a la fehca inicio
    fun validarFechas(
        fechaInicio: EditText,
        horaInicio: EditText,
        fechaFin: EditText,
        horaFin: EditText
    ): Boolean {
        var fecha1 = LocalDateTime.parse(
            "${fechaInicio.text.toString()} ${horaInicio.text.toString()}",
            DateTimeFormatter.ofPattern(
                "dd/MM/yyyy hh:mm:ss a", Locale.ENGLISH
            )
        )
        var fecha2 = LocalDateTime.parse(
            "${fechaFin.text.toString()} ${horaFin.text.toString()}", DateTimeFormatter.ofPattern(
                "dd/MM/yyyy hh:mm:ss a", Locale.ENGLISH
            )
        )
        return fecha2.isBefore(fecha1)
    }

    //Función para obtener el radiobutton seleccionado por el usuario
    private fun obtenerRadioButton(
        tipoEventoGroup: RadioGroup,
        cita: RadioButton,
        aniversario: RadioButton,
        cuentaAtras: RadioButton
    ): String {

        var tipoEvento = when (tipoEventoGroup.checkedRadioButtonId) {
            cita.id -> cita.text.toString()
            aniversario.id -> aniversario.text.toString()
            else -> cuentaAtras.text.toString()
        }
        return tipoEvento
    }

    //Función para contruir el resumen del evento a guardar.
    private fun obtenerResumen(
        nombreEvento: EditText,
        fechaInicio: EditText,
        horaInicio: EditText,
        fechaFin: EditText,
        horaFin: EditText,
        todoElDia: Switch,
        descripcion: EditText,
        tipoEventoGroup: RadioGroup,
        cita: RadioButton,
        aniversario: RadioButton,
        cuentaAtras: RadioButton
    ): String {
        return "<ul>" +
                "<li><b><font color='#09234f'>Nombre del evento: </font></b>${nombreEvento.text.toString()}<br></li>" +
                "<li><b>Tipo de evento: </b>${
                    obtenerRadioButton(
                        tipoEventoGroup,
                        cita,
                        aniversario,
                        cuentaAtras
                    )
                }<br></li>" +
                "<li><b>Fecha y hora de inicio: </b>${fechaInicio.text.toString()} ${horaInicio.text.toString()}<br></li>" +
                "<li><b>Fecha y hora final: </b>${fechaFin.text.toString()} ${horaFin.text.toString()}<br></li>" +
                "<li><b>Todo el día: </b>${if (todoElDia.isChecked) "Si" else "No"}<br></li>" +
                "${if (descripcion.text.isNotEmpty() && descripcion.text.isNotBlank()) "<li><b>Descripción del evento: </b> ${descripcion.text.toString()}</li>" else ""}" +
                "</ul><hr>"
    }

    //Función que permite ocultar el teclado
    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideMe.hideSoftInputFromWindow(view.windowToken, 0)
        } else {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
    }

}