package com.example.cabo.tp4.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cabo.tp4.auth.AuthHelper
import com.example.cabo.tp4.dao.FirestoreHelper
import com.example.cabo.tp4.model.Comida

class RegistroViewModel : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData <Boolean> = _loading

    private val _listaComida = MutableLiveData<List<Comida>>()
    val listaComida : LiveData <List<Comida>> = _listaComida

    fun getComidas(type: String) {
        _loading.postValue(true)
        val db = FirestoreHelper()
        _loading.postValue(true)
        AuthHelper.instance.persona.uuid?.let { it ->
            db.getComidasByType(it, type) { lista ->
                _listaComida.postValue(lista)
                _loading.postValue(false)
            }
        }
    }
}