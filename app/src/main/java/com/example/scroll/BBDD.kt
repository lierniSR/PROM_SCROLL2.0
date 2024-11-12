package com.example.scroll

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Creacion de la base de datos y sentencias SQL
 *
 * @param context
 * @author Lierni
 * @version 2.0
 */
class BBDD (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "tareas.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "tareas"
        //Las columnas de la tabla
        private const val  COLUMN_ID = "id"
        private const val  COLUMN_DESCRIPCION = "descripcion"
    }

    /**
     * Creacion de la tabla
     */
    override fun onCreate(db: SQLiteDatabase?) {
        val createTablQuery = "CREATE TABLE $TABLE_NAME(" +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_DESCRIPCION TEXT)"
        db?.execSQL(createTablQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, viejaVerion: Int, nuevaVersion: Int) {
        val dropTablaQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTablaQuery)

        //Despues de eliminar creamos la tabla para no tener errores.
        onCreate(db)
    }

    fun insertarTarea(tarea: Tarea){
        //Abrimos en modo escritura
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ID, tarea.id)
            put(COLUMN_DESCRIPCION, tarea.descripcion)
        }

        //Insertamos datos
        db.insert(TABLE_NAME,null, values)
        //Cerramos BBDD
        db.close()
    }

    fun getTareas() : List<Tarea>{
        val listasTarea = mutableListOf<Tarea>()

        //Abrimos en modo lectura
        val db = readableDatabase

        val query = "SELECT * FROM $TABLE_NAME"

        //Lanzar un cursor
        val cursor = db.rawQuery(query, null)

        //Iterar y insertar las tareas a la lista
        while(cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION))

            //Creamo objeto tarea
            val tarea = Tarea(id, descripcion)

            //Añadirmos a la lista
            listasTarea.add(tarea)
        }

        //Cerramos las conexiones
        cursor.close()
        db.close()


        return listasTarea
    }

    /**
     * Eliminar una tarea dada su ID
     */
    fun deleteTarea(idTarea : Int){
        val db = writableDatabase

        val whereClauses = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(idTarea.toString())

        //Eliminar objeto
        db.delete(TABLE_NAME, whereClauses, whereArgs)

        db.close()
    }
}