package com.example.cabo.tp4.viewmodel

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cabo.tp4.auth.AuthHelper
import com.example.cabo.tp4.utils.FieldValidation


class SignUpViewModel : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _mailValidation = MutableLiveData<FieldValidation>()
    val mailValidation: LiveData<FieldValidation> = _mailValidation

    private val _passwordValidation = MutableLiveData<FieldValidation>()
    val passwordValidation: LiveData<FieldValidation> =
        _passwordValidation

    private val _registrationStatus = MutableLiveData<Boolean>()
    val registrationStatus: LiveData<Boolean> = _registrationStatus

    fun signUp(email: String, password: String, password_confirmation: String) {
        _loading.postValue(true)
        if (isEmailValid(email) && isPasswordValid(password, password_confirmation)) {
            try {
                AuthHelper.instance.createUserWithEmailAndPassword(email, password) {
                   _registrationStatus.postValue(it)
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

    private fun isEmailValid(email: String): Boolean {
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

    private fun isPasswordValid(password: String, password_confirmation: String): Boolean {
        when {
            password.length < 6 -> {
                _passwordValidation.postValue(FieldValidation.PASSWORD_LENGHT_FAIL)
                return false
            }
            password != password_confirmation -> {
                _passwordValidation.postValue(FieldValidation.PASSWORD_NOT_EQUAL)
                return false
            }
            else -> {
                _passwordValidation.postValue(FieldValidation.SUCCESS)
                return true
            }
        }
    }
}