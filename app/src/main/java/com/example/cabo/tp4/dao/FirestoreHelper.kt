package com.example.cabo.tp4.dao

import android.util.Log
import com.example.cabo.tp4.model.Comida
import com.example.cabo.tp4.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class FirestoreHelper : ApiProtocol {


    private val collectionPersonas = Firebase.firestore.collection("personas")
    private val collectionComidas = Firebase.firestore.collection("comidas")

    override fun insertUserData(user: User, uuid: String, callback: (Boolean) -> Unit) {
        collectionPersonas.document(uuid).set(user).addOnSuccessListener {
            callback(true)
        }
    }

    override fun getUserData(uuid: String, callback: (User?) -> Unit) {
        collectionPersonas.document(uuid).get().addOnSuccessListener {
            val user = it.toObject<User>()
            callback(user)
            Log.e("cache", it.metadata.isFromCache.toString() )
        }
    }

    override fun insertComidaData(comida: Comida, uuid: String, callback: (Boolean) -> Unit) {
        collectionComidas.document(uuid).collection(comida.tipo.name).add(comida).addOnSuccessListener {
            callback(true)
        }
    }
}

