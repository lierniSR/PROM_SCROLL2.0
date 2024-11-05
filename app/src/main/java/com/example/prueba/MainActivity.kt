package com.example.prueba

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prueba.TareasAplicacion.Companion.prefs

/**
 * App para añadir tareas, incluyendo la hora.
 * Sonido al añadir.
 * Sonido al eliminar.
 * No se pueden añadir tareas vacías.
 * Sale una alerta de error si la tarea está vacia.
 *
 * @author Lierni
 * @version 2.0
 */

class MainActivity : AppCompatActivity() {
    /**
     * Se inicializa después con el ID del xml
     */
    lateinit var textoEdit: EditText
    lateinit var buttonTareaAdd: Button
    lateinit var recyclerTareas: RecyclerView
    lateinit var adapter:AdapterTareas
    //Para el sonido crear raw en res y añadir los sonidos ahi
    lateinit var checkSonido: MediaPlayer
    lateinit var eliminarSonido: MediaPlayer
    lateinit var horario: String


    var tareas = mutableListOf<Tarea>()
    val db:BBDD = BBDD(this)



    /**
     * Crea la vista de la aplicacion
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textoEdit = findViewById(R.id.texto)
        checkSonido = MediaPlayer.create(this, R.raw.ok)
        eliminarSonido = MediaPlayer.create(this, R.raw.cash)
        //Inicializamos la base de datos
        initUi()
    }

    /**
     * Metodo para inicializar toda la logica de la app
     */
    private fun initUi() {
        initView();
        initListeners()
        initRecyclerView()
    }

    /**
     * Este metodo hara que el RecyclerView se muestre bien
     */
    private fun initRecyclerView() {
        tareas = db.getTareas().toMutableList()
        recyclerTareas.layoutManager = LinearLayoutManager(this)
        adapter = AdapterTareas(tareas, {eliminarTarea(it)})
        recyclerTareas.adapter = adapter
    }

    /**
     * Funcion que elimina la tarea
     *
     * @param posicion
     */
    private fun eliminarTarea(posicion:Int){
        tareas.removeAt(posicion)
        db.eliminarTarea(posicion)
        tareas = db.getTareas().toMutableList()
        adapter.notifyDataSetChanged()
        eliminarSonido.start()
    }
    /**
     * Metodo para crear los listener
     */
    private fun initListeners() {
        buttonTareaAdd.setOnClickListener{ aniadeTarea() }
    }

    /**
     * Metodo que llama el listener.
     * Lo añade a la lista para luego añadirlo a RecycledView
     * Para añadirlo se necesita un adapter y el viewHolder
     */
    private fun aniadeTarea() {
        if(textoEdit.text.isNotEmpty()){
            val tareaAAniadir = textoEdit.text.toString()
            val tarea = Tarea(1, tareaAAniadir)
            tareas.add(tarea)
            db.insertarTarea(tareaAAniadir)
            tareas = db.getTareas().toMutableList()
            adapter.notifyDataSetChanged()
            textoEdit.setText("")
            checkSonido.start()
        } else{
            mostrarAlerta(this)
        }
    }

    /**
     * Metodo que muestra una alerta de error si el campo está vacio
     *
     * @param contexto
     */
    private fun mostrarAlerta(contexto: Context) {
        val builder = AlertDialog.Builder(contexto)
        builder.setTitle("Error")
            .setMessage("La tarea está vacia...")
            .setPositiveButton("Aceptar") { dialog, which ->
                dialog.dismiss()
            }

        val dialog = builder.create()
        dialog.show()
    }

    /**
     * Metodo para crear la vista con los componentes
     */
    private fun initView() {
        buttonTareaAdd = findViewById(R.id.buttonTareaAdd)
        recyclerTareas = findViewById(R.id.recyclerTareas)
    }

    /**
     * Esta funcion llama a TimePicker de la clase de la propia aplicacion
     * Después abre la ventana para seleccionar la hora
     *
     * @param view
     */
    fun seleccionarHora(view: View) {
        val hora = TimePicker {hora, dia -> mostrarResultado(hora,dia)}
        hora.show(supportFragmentManager, "TimePicker")
    }

    /**
     * Esta funcion añade la hora a lo que el usuario introduce
     * La muestra despues en el textEdit
     *
     * @param hora
     * @param minuto
     */
    fun mostrarResultado(hora: Int, minuto: Int){
        horario = "$hora:$minuto"
        var texto = textoEdit.text
        textoEdit.setText("$texto --> $horario")
    }
}