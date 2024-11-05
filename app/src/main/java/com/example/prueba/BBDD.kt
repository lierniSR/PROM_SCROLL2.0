package com.example.prueba

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BBDD (context: Context) :  SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
){
    companion object{
        private const val DATABASE_NAME = "tareas.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "tareas"
        //Ahora las columnas
        private const val COLUMN_ID = "id"
        private const val COLUMN_TAREA = "tarea"
    }

    /**
     * Crea la tabla
     */
    override fun onCreate(db: SQLiteDatabase?) {
        val creacionTabLayout = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_TAREA TEXT)"
        db?.execSQL(creacionTabLayout)
    }

    /**
     * Mira en la base de datos si hay alguna tabla con ese nombre, si existe una tabla con ese nobre se eliminara
     */
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val dropTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db)
    }

    /**
     * Inserta la tarea en la base de datos
     */
    fun insertarTarea(tarea: String) {
        //Abrimos en modo lectura
        val db = writableDatabase
        val valores = ContentValues().apply {
            put(COLUMN_TAREA, tarea)
        }

        //Insertamos los datos
        db.insert(TABLE_NAME, null, valores)
        //Cerramos BD
        db.close()
    }

    /**
     * Elimina la tarea de la base de datos
     */
    fun eliminarTarea(idNota : Int){
        //Abrimos modo lectura
        val db = writableDatabase

        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(idNota.toString())
        //Eliminar la tarea
        db.delete(TABLE_NAME, whereClause, whereArgs)
        //Cerramos BD
        db.close()
    }

    /**
     * Visualiza las tareas de la base de datos
     */
    fun getTareas(): List<Tarea>{
        //Se crea una lista mutable
        val tareas = mutableListOf<Tarea>()
        //Abrimos en modo lectura
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        //Recorremos el cursor hasta que no tenga next
        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val tituloTarea = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TAREA))
            val tarea = Tarea(id, tituloTarea)
            tareas.add(tarea)
        }
        //Cerramos las conexiones
        cursor.close()
        db.close()

        return tareas
    }
}