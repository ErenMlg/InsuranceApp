package com.softcross.insuranceapp.domain.repository

import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.domain.model.User
import com.softcross.insuranceapp.domain.model.UserType
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {

    fun loginUser(email: String, password: String): Flow<ScreenState<User>>
    fun registerUser(
        email: String,
        password: String,
        name: String,
        surname: String,
        role: UserType
    ): Flow<ScreenState<User>>

    suspend fun addUserDetailsToFirestore(userModel: User)
    suspend fun getUserDetailFromFirestore(userID: String): User

    suspend fun sendResetPasswordEmail(email: String): Flow<ScreenState<Boolean>>


}