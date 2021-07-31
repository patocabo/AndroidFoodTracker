package com.example.cabo.tp4.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cabo.tp4.model.User
import com.example.cabo.tp4.auth.AuthHelper
import com.example.cabo.tp4.dao.FirestoreHelper

class HomeViewModel : ViewModel() {

    private val _usuario = MutableLiveData<User?>()
    val usuario: LiveData<User?> = _usuario

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _signOutStatus = MutableLiveData<Result<Boolean>>()
    val signOutStatus: LiveData<Result<Boolean>> = _signOutStatus

    private val _deleted = MutableLiveData<Boolean>()
    val deleted: LiveData<Boolean> = _deleted

    fun signOut() {
        _loading.postValue(true)
        try {
            AuthHelper.instance.signOut()
            _signOutStatus.postValue(Result.success(true))
            _loading.postValue(false)
        } catch (e: Exception) {
            e.printStackTrace()
            _loading.postValue(false)
            _signOutStatus.postValue(Result.failure(e))
        }
    }

    fun loadUserData() {
        _loading.postValue(true)
        AuthHelper.instance.persona.uuid?.let {
            val db = FirestoreHelper()
            db.getUserData(it) { user ->
                _usuario.postValue(user)
                _loading.postValue(false)
            }
        }
    }

    fun deleteUserDate() {
        AuthHelper.instance.persona.uuid?.let {
            val db = FirestoreHelper()
            db.deleteUserData(it)
        }
    }

}