package com.example.cabo.tp4.auth


interface AuthInterface {
    fun signOut()
    fun signIn(email: String, password: String, callback: (Boolean) -> Unit)
    fun createUserWithEmailAndPassword(email: String, password: String, callback: (Boolean) -> Unit)
}