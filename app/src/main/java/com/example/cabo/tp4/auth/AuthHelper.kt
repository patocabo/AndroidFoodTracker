package com.example.cabo.tp4.auth

import com.example.cabo.tp4.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthHelper : AuthInterface {
    private val auth: FirebaseAuth = Firebase.auth
    var persona = User()

    companion object {
        val instance = AuthHelper()
    }

    init {
        auth.addAuthStateListener {
            persona.uuid = it.currentUser?.uid
        }
    }

    override fun signOut() {
        auth.signOut()
        persona.uuid = null
    }

    override fun signIn(email: String, password: String, callback: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            callback(it.isSuccessful)
        }
    }

    override fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        callback: (Boolean) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            callback(it.isSuccessful)
        }
    }


}