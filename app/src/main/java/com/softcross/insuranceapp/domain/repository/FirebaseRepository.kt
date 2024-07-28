package com.softcross.insuranceapp.domain.repository

import com.softcross.insuranceapp.common.ResponseState
import com.softcross.insuranceapp.domain.model.User
import com.softcross.insuranceapp.domain.model.UserType
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {

    fun loginUser(email: String, password: String): Flow<ResponseState<User>>
    fun registerUser(
        email: String,
        password: String,
        name: String,
        surname: String,
        role: UserType
    ): Flow<ResponseState<User>>

    suspend fun addUserDetailsToFirestore(userModel: User)
    suspend fun getUserDetailFromFirestore(userID: String): User

    suspend fun sendResetPasswordEmail(email: String): Flow<ResponseState<Boolean>>


}