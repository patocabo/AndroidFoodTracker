package com.example.cabo.tp4.dao

import com.example.cabo.tp4.model.Comida
import com.example.cabo.tp4.model.User
import javax.security.auth.callback.Callback


interface ApiProtocol {

    fun insertUserData(user: User, uuid: String, callback: (Boolean) -> Unit)
    fun getUserData(uuid: String, callback: (User?) -> Unit)
    fun insertComidaData(comida: Comida, uuid: String, callback: (Boolean) -> Unit)
}