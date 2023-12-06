package com.example.fruticion

import android.app.Application
import com.example.fruticion.util.AppContainer

//Esta clase sirve unicamente para tener e inicializar la instancia del contenedor de dependencias, y, por tanto, a su vez inicializará las dependencias
//Esta clase hay que indicarla en el AndroidManifest.xml porque hereda de Application(), por tanto, hay que declararla ahi dentro de la etiqueta <application> usando el atributo android:name
//Gracias a declararlo en el AndroidManifest.xml en la etiqueta <application> Android se encargara de gestionar esta clase de forma automatica de manera que inicializara automaticamente las dependencias una sola vez sin que sea necesario hacer nada mas.
class FruticionApplication : Application(){
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer (this)
    }
}