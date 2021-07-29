package com.example.cabo.tp4.viewmodel

import androidx.lifecycle.*
import com.example.cabo.tp4.auth.AuthHelper
import com.example.cabo.tp4.dao.FirestoreHelper
import com.example.cabo.tp4.model.Sexo
import com.example.cabo.tp4.model.Tratamiento
import com.example.cabo.tp4.model.User
import java.util.*


class ProfileViewModel : ViewModel() {
    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _dataInserted = MutableLiveData<Boolean>()
    val dataInserted: LiveData<Boolean> = _dataInserted

    fun saveData(
        nombre: String,
        apellido: String,
        dni: Long,
        sexo: String,
        fechNac: String,
        localidad: String? = "",
        tratamiento: String,
    ) {
        val db = FirestoreHelper()
        val persona = User(
            nombre,
            apellido,
            dni,
            Sexo.valueOf(sexo),
            Date(fechNac),
            localidad,
            "",
            "",
            Tratamiento.valueOf(tratamiento),
        )
        db.insertUserData(persona, AuthHelper.instance.persona.uuid!!){
        }
            _dataInserted.postValue(true)
    }


    private val _usuario = MutableLiveData<User?>()
    val usuario: LiveData<User?> = _usuario

    fun loadUserData() {
        _loading.postValue(true)
        AuthHelper.instance.persona.uuid?.let {
            val db = FirestoreHelper()
            db.getUserData(AuthHelper.instance.persona.uuid!!) {
                _usuario.postValue(it)
                _loading.postValue(false)

            }
        }
    }


}