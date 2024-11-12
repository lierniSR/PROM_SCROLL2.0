package com.example.scroll

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Layout o estilo del recyclerView
 *
 * @param view
 * @author Lierni
 * @version 2.0
 */
class TareasViewHolder(view : View): RecyclerView.ViewHolder(view) {

    private val textViewTarea: TextView = view.findViewById(R.id.textViewTarea)
    private val imagenBorrar: ImageView = view.findViewById(R.id.imagenBorrar)

    /**
     * Se encarga de isnertar en el textview el texto que le llega como parametro
     *
     * @param tarea
     * @param onItemDone
     */
    fun insertarTarea(tarea:String, onItemDone:(Int) -> Unit){
        textViewTarea.text = tarea
        imagenBorrar.setOnClickListener{ onItemDone(adapterPosition) }
    }

}