package com.example.cabo.tp4.model

import java.io.Serializable

data class Bebida(
    val drinks: List<Drink>
)

data class Drink(
    val strCategory: String,
    val strDrink: String,
    val strDrinkThumb: String,
): Serializable