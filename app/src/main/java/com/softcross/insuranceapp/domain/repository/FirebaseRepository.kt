package com.softcross.insuranceapp.domain.repository

import com.softcross.insuranceapp.common.Resource
import com.softcross.insuranceapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {

    fun loginUser(email: String, password: String): Flow<Resource<User>>
    fun registerUser(
        email: String,
        password: String,
        name: String,
        surname: String
    ): Flow<Resource<User>>

    suspend fun addUserDetailsToFirestore(userModel: User)
    suspend fun getUserDetailFromFirestore(userID: String): User


}