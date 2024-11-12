package com.example.scroll

import android.app.Dialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment

/**
 * Clase TimePicker para la ventana modal de la hora.
 * Devuelve un dialogFragment() y un TimePickerDialog.OnTimeSetListener
 *
 * @param listener
 * @return DialogFragment()
 * @return TimePickerDialog.OnTimeSetListener
 * @author Lierni
 * @version 2.0
 */
class TimePicker(val listener: (hora: Int, minuto:Int) -> Unit) : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    /**
     * Al listener que llega como parametro se le pasa los parametros horaDia y minuto
     *
     * @param view
     * @param horaDia
     * @param minuto
     */
    override fun onTimeSet(view: TimePicker?, horaDia: Int, minuto: Int) {
        listener(horaDia, minuto)
    }

    /**
     * Esta funcion es para que el dialogo se abra con la hora del sistema
     *
     * @param saveInstanceState
     * @return Dialog
     */
    override fun onCreateDialog(saveInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hora = c.get(Calendar.HOUR_OF_DAY)
        val minuto = c.get(Calendar.MINUTE)

        return TimePickerDialog(activity, this, hora, minuto, DateFormat.is24HourFormat(activity))
    }
}