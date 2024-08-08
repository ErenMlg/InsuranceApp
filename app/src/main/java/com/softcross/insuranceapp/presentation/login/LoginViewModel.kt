package com.softcross.insuranceapp.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.domain.model.User
import com.softcross.insuranceapp.domain.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _loginUiState = mutableStateOf<ScreenState<User>>(ScreenState.Loading)
    val loginUiState: State<ScreenState<User>> = _loginUiState

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        firebaseRepository.loginUser(email, password).collect { result ->
            when (result) {
                is ScreenState.Loading -> _loginUiState.value = ScreenState.Loading
                is ScreenState.Success -> _loginUiState.value = ScreenState.Success(result.data)
                is ScreenState.Error -> _loginUiState.value =
                    ScreenState.Error(result.message)
            }
        }
    }

}