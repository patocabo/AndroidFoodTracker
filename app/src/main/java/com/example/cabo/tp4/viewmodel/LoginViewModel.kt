package com.example.cabo.tp4.viewmodel

import android.text.TextUtils
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

    private val _signInStatus = MutableLiveData<Result<Boolean>>()
    val signInStatus: LiveData<Result<Boolean>> = _signInStatus

    fun signIn(email: String, password: String) {
        _loading.postValue(true)
        if (isPasswordValid(password) && isEmailValid(email)) {
            try {
                AuthHelper.instance.signIn(email, password) {
                    if (it) {
                        _signInStatus.postValue(Result.success(true))
                    } else {
                        _signInStatus.postValue(Result.success(false))
                    }
                    _loading.postValue(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _signInStatus.postValue(Result.failure(e))
                _loading.postValue(false)
            }
        } else {
            _loading.postValue(false)
        }
    }

    private fun isEmailValid(email: String): Boolean { //TODO Agregar validaciones
        when {
            TextUtils.isEmpty(email) -> {
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

    private fun isPasswordValid(password: String): Boolean { //TODO Agregar validaciones password
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