package com.example.cabo.tp4.api

import com.example.cabo.tp4.model.Bebida
import retrofit2.Call
import retrofit2.http.GET

interface ApiBebidas {
    @GET("api/json/v1/1/random.php")
    fun getTrago() : Call<Bebida>
}