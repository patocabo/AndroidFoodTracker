package com.example.cabo.tp4.model

import java.io.Serializable
import java.util.*


data class Comida(
    var tipo: TipoComida? = TipoComida.ALMUERZO,
    var principal: String? = "",
    var secundaria: String? = "",
    var bebida: String? = "",
    var tienePostre: Boolean? = true,
    var postre: String? = "",
    var tieneTentacion: Boolean? = true,
    var tentacion: String? = "",
    var tieneHambre: Boolean? = true,
    var created: Date? = Date(),
    var fecha: String? = ""
) : Serializable

enum class TipoComida {
    DESAYUNO, ALMUERZO, MERIENDA, CENA
}