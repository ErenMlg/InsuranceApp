package com.softcross.insuranceapp.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.insuranceapp.common.ResponseState
import com.softcross.insuranceapp.domain.model.User
import com.softcross.insuranceapp.domain.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _loginUiState = mutableStateOf<ResponseState<User>>(ResponseState.Loading)
    val loginUiState: State<ResponseState<User>> = _loginUiState

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        firebaseRepository.loginUser(email, password).collect { result ->
            when (result) {
                is ResponseState.Loading -> _loginUiState.value = ResponseState.Loading
                is ResponseState.Success -> _loginUiState.value = ResponseState.Success(result.data)
                is ResponseState.Error -> _loginUiState.value =
                    ResponseState.Error(result.message)
            }
        }
    }

}