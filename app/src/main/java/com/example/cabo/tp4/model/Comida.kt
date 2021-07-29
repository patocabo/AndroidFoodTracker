package com.example.cabo.tp4.model

import java.io.Serializable
import java.util.*


data class Comida(
    var tipo: TipoComida,
    var principal: String,
    var secundaria: String,
    var bebida: String,
    var tienePostre: Boolean,
    var postre: String? = "",
    var tieneTentacion: Boolean,
    var tentacion: String? = "",
    var tieneHambre: Boolean,
    var created: Date,
    var fecha: String
) : Serializable

enum class TipoComida {
    DESAYUNO, ALMUERZO, MERIENDA, CENA
}