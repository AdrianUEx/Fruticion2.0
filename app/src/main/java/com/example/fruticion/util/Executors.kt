package com.example.fruticion.util

import java.util.concurrent.Executors

//Executors sirve simplemente para declararse un pool de threads secundarios que se encarguen de las llamadas remotas (tareas pesadas), sea a la API o a imagenes remotas con Glide
val BACKGROUND = Executors.newFixedThreadPool(2)