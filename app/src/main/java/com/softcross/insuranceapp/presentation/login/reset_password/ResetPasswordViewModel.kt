package com.softcross.insuranceapp.presentation.login.reset_password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.domain.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _resetPasswordState = mutableStateOf<ScreenState<Boolean>>(ScreenState.Loading)
    val resetPasswordState : State<ScreenState<Boolean>> = _resetPasswordState

    fun resetPassword(email: String) = viewModelScope.launch {
        firebaseRepository.sendResetPasswordEmail(email).collect { response ->
            when (response) {
                is ScreenState.Loading -> _resetPasswordState.value = ScreenState.Loading

                is ScreenState.Success -> _resetPasswordState.value =
                    ScreenState.Success(response.data)

                is ScreenState.Error -> _resetPasswordState.value =
                    ScreenState.Error(response.message)
            }
        }
    }

}