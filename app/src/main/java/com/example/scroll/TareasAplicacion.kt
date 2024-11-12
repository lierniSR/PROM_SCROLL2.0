package com.example.scroll

import android.app.Application

/**
 * Para instanciar cosas que necesitamos para todo el proyecto
 * manifests/AndroidManifest.xml android:name = ".TareasAplicacion"
 *
 * @author Lierni
 * @version 2.0
 */
class TareasAplicacion: Application() {
    //companion = static
    companion object{
        lateinit var prefs:Preferencias
    }

    /**
     * Metodo de Application()
     */
    override fun onCreate() {
        super.onCreate()
        prefs = Preferencias(baseContext)
    }
}