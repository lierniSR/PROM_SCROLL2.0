package com.example.prueba

import android.content.Context
import android.content.SharedPreferences

/**
 * Se almacena toda la logica de Shared Preferences
 *
 * @param contexto
 * @author Lierni
 * @version 2.0
 */
class Preferencias(contexto: Context) {
    //companion = static
    companion object{
        const val PREFS_NAME = "ScrollDataBase"
        const val TAREAS = "tareas_valor"
    }

    val prefs:SharedPreferences = contexto.getSharedPreferences(PREFS_NAME, 0)

    /**
     * Para guardar una lista de String en el Shared Preferences
     *
     * @param tareas
     */
    fun guardarTareas(tareas:List<String>){
        //apply() para que se apliquen los cambios
        prefs.edit().putStringSet(TAREAS, tareas.toSet()).apply()
    }

    /**
     * Recuperar la informacion del Shared Preferences
     *
     *@return List<String>
     */
    fun recuperarTareas():MutableList<String>{
        //!! para decirle que no va a ser nulo
        //?: Para decirlo que si es nulo ponga el valor default que se pone despues
        return prefs.getStringSet(TAREAS, emptySet<String>())!!.toMutableList()
    }
}