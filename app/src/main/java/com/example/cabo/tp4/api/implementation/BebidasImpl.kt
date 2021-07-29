package com.example.cabo.tp4.api.implementation

import com.example.cabo.tp4.api.ApiBebidas
import com.example.cabo.tp4.model.Bebida
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class BebidasImpl {
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.thecocktaildb.com/")
            .build()
    }

    fun getBebidaRandom(): Call<Bebida> {
        return getRetrofit().create(ApiBebidas::class.java)
            .getTrago()
    }
}