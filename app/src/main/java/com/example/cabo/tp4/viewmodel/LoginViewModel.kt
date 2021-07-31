package com.example.cabo.tp4.viewmodel

import android.util.Patterns
import androidx.lifecycle.*
import com.example.cabo.tp4.auth.AuthHelper
import com.example.cabo.tp4.utils.FieldValidation
import java.lang.Exception


class LoginViewModel : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _mailValidation = MutableLiveData<FieldValidation>()
    val mailValidation: LiveData<FieldValidation> = _mailValidation

    private val _passwordValidation = MutableLiveData<FieldValidation>()
    val passwordValidation: LiveData<FieldValidation> = _passwordValidation

    private val _signInStatus = MutableLiveData<Boolean>()
    val signInStatus: LiveData<Boolean> = _signInStatus

    fun signIn(email: String, password: String) {
        _loading.postValue(true)
        if (isPasswordValid(password) && isEmailValid(email)) {
            try {
                AuthHelper.instance.signIn(email, password) {
                    _signInStatus.postValue(it)
                    _loading.postValue(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _loading.postValue(false)
            }
        } else {
            _loading.postValue(false)
        }
    }

    fun isEmailValid(email: String): Boolean {
        when {
            email.isEmpty() -> {
                _mailValidation.postValue(FieldValidation.EMPTY)
                return false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _mailValidation.postValue(
                    FieldValidation.EMAIL_BAD_FORMAT
                )
                return false
            }
            else -> {
                _mailValidation.postValue(FieldValidation.SUCCESS)
                return true
            }
        }
    }

    fun isPasswordValid(password: String): Boolean { //TODO Agregar validaciones password
        when {
            password.length < 6 -> {
                _passwordValidation.postValue(FieldValidation.PASSWORD_LENGHT_FAIL)
                return false
            }
            else -> {
                _passwordValidation.postValue(FieldValidation.SUCCESS)
                return true
            }
        }
    }
}