package com.example.cabo.tp4.model

import java.io.Serializable
import java.util.*

data class User(
    var nombre: String? = "",
    var apellido: String? = "",
    var dni: Long? = 0,
    var sexo: Sexo? = Sexo.MASCULINO,
    var fechNac: String = "",
    var localidad: String? = "",
    var usuario: String? = "",
    var contrasena: String? = "",
    var tratamiento: Tratamiento? = Tratamiento.ANOREXIA,
    var uuid: String? = null

) : Serializable

enum class Tratamiento {
    ANOREXIA, BULIMIA, OBESIDAD
}

enum class Sexo {
    MASCULINO, FEMENINO
}