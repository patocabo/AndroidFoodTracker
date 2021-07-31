package com.example.cabo.tp4.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.example.cabo.tp4.api.implementation.BebidasImpl
import com.example.cabo.tp4.auth.AuthHelper
import com.example.cabo.tp4.dao.FirestoreHelper
import com.example.cabo.tp4.model.*
import com.example.cabo.tp4.utils.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ComidaViewModel : ViewModel() {

    private val _dataInserted = MutableLiveData<Boolean>()
    val dataInserted: LiveData<Boolean> = _dataInserted

    fun saveComidaData(
        tipo: String,
        principal: String,
        secundaria: String,
        bebida: String,
        tienePostre: Boolean,
        postre: String,
        tieneTentacion: Boolean,
        tentacion: String,
        tieneHambre: Boolean,
        fecha: String

    ) {
        val db = FirestoreHelper()
        val comida = Comida(
            TipoComida.valueOf(tipo),
            principal,
            secundaria,
            bebida,
            tienePostre,
            postre,
            tieneTentacion,
            tentacion,
            tieneHambre,
            Date(),
            fecha
        )
        db.insertComidaData(comida, AuthHelper.instance.persona.uuid!!) {
        }
        _dataInserted.postValue(true)
    }

    private val _bebida = MutableLiveData<Bebida?>()
    val bebida: LiveData<Bebida?> = _bebida

    fun getBebidaFromApi(context: Context) {
        val api = BebidasImpl()
        val util = Util()
        if (util.isDeviceOnline(context)) {
            api.getBebidaRandom().enqueue(object : Callback<Bebida> {
                override fun onResponse(call: Call<Bebida>, response: Response<Bebida>) {
                    _bebida.postValue(response.body())
                }

                override fun onFailure(call: Call<Bebida>, t: Throwable) {
                    _bebida.postValue(null)
                }

            })
        }
    }
}