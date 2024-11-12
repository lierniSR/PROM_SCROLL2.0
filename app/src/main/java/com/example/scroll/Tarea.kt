package com.example.scroll

/**
 * Como va aser una clase para guardar datos la precedemos por data,
 *
 * @param id Identificador
 * @param descripcion Decripcion
 */
data class Tarea (val id: Int, val descripcion:String){
    override fun toString(): String {
        return "$descripcion"
    }
}

