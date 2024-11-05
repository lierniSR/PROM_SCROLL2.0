package com.example.prueba

/**
 * Esta clase es para hacer referencia a un objeto tipo Tarea
 *
 * @param id
 * @param tarea
 */
data class Tarea(val id:Int, val tarea:String){
    override fun toString(): String {
        return tarea
    }

}