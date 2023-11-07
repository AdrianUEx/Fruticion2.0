package com.example.fruticion.util

import java.util.concurrent.Executors

//Executors sirve simplemente para declararse un pool de threads secundarios que se encarguen de las llamadas remotas (tareas pesadas), sea a la API o a imagenes remotas con Glide
//Esta variable es muy posible que no haya que usarla si se hace la segunda parte del lab con lifecycleScope
val BACKGROUND = Executors.newFixedThreadPool(2)