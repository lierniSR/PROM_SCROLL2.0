package com.example.scroll

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
/**
 * Este adapter conectara el viewHolder con toda la aplicacion
 * @param tareas
 * @param onItemDone
 * @author Lierni
 * @version 2.0
 */
class AdapterTareas(private val tareas:List<Tarea>, private val onItemDone: (Int) -> Unit):
    RecyclerView.Adapter<TareasViewHolder>() {
    /**
     * Este metodo crea ViewHolder, y le asigne el item Task con ViewHolder
     *
     * @param parent
     * @param viewType
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TareasViewHolder(layoutInflater.inflate(R.layout.item_tarea, parent, false))
    }

    /**
     * Este metodo es para hacer el scroll
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: TareasViewHolder, position: Int) {
        holder.insertarTarea(tareas[position].toString(), onItemDone)
    }

    /**
     * Muestra al viewHolder cuantos items tiene que mostrar
     * Devuelve el tamaño del listado
     *
     * @return Int
     */
    override fun getItemCount(): Int {
        return tareas.size
    }

}