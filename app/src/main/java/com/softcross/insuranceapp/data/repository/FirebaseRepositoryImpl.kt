package com.softcross.insuranceapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.softcross.insuranceapp.common.ResponseState
import com.softcross.insuranceapp.domain.model.User
import com.softcross.insuranceapp.domain.model.UserType
import com.softcross.insuranceapp.domain.repository.FirebaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : FirebaseRepository {

    override fun loginUser(email: String, password: String): Flow<ResponseState<User>> {
        return flow {
            emit(ResponseState.Loading)
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            result.user?.let {
                val loggedUser = getUserDetailFromFirestore(it.uid)
                emit(ResponseState.Success(loggedUser))
            }
        }.catch {
            emit(ResponseState.Error(it.message.toString()))
        }
    }

    override fun registerUser(
        email: String,
        password: String,
        name: String,
        surname: String,
        role: UserType
    ): Flow<ResponseState<User>> {
        return flow {
            emit(ResponseState.Loading)
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.let {
                val userModel = User(result.user!!.uid, name, surname, role)
                addUserDetailsToFirestore(userModel)
                emit(ResponseState.Success(userModel))
            }
        }.catch {
            emit(ResponseState.Error(it.message.toString()))
        }
    }

    override suspend fun addUserDetailsToFirestore(userModel: User) {
        val firestoreUsers = firebaseFirestore.collection("Users").document(userModel.id)
        firestoreUsers.set(
            hashMapOf(
                "name" to userModel.name,
                "surname" to userModel.surname,
                "role" to userModel.userType.ordinal
            )
        ).addOnFailureListener {
            throw Exception(it.message)
        }.await()
    }

    override suspend fun getUserDetailFromFirestore(userID: String): User {
        val firestoreDoc = firebaseFirestore.collection("Users").document(userID).get().await()
        if (firestoreDoc.data != null) {
            val name = firestoreDoc.data?.get("name").toString()
            val surname = firestoreDoc.data?.get("surname").toString()
            val role = when (firestoreDoc.data?.get("role").toString().toInt()) {
                1 -> UserType.ADMIN
                2 -> UserType.USER
                else -> UserType.UNSPECIFIED
            }
            return User(userID, name, surname, role)
        } else {
            throw Exception("Something went wrong while getting user details from firestore")
        }
    }

    override suspend fun sendResetPasswordEmail(email: String): Flow<ResponseState<Boolean>> {
        return flow {
            emit(ResponseState.Loading)
            try {
                firebaseAuth.sendPasswordResetEmail(email).await()
                emit(ResponseState.Success(true))
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message.toString()))
            }
        }
    }
}