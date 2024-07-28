package com.softcross.insuranceapp.common

import com.softcross.insuranceapp.domain.model.User

object CurrentUser {

    private var currentUser: User? = null

    fun setCurrentUser(user: User) {
        currentUser = user
    }

    fun getCurrentUser(): User? {
        return currentUser
    }

    fun removeCurrentUser() {
        currentUser = null
    }

    fun getCurrentUserID(): String = currentUser?.id ?: ""

    fun getCurrentUserName(): String = "${currentUser?.name} ${currentUser?.surname?.first()}."
}