package com.softcross.insuranceapp.presentation.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.insuranceapp.common.AllLocations
import com.softcross.insuranceapp.common.AllMakes
import com.softcross.insuranceapp.common.CurrentUser
import com.softcross.insuranceapp.common.NetworkResponseState
import com.softcross.insuranceapp.common.ScreenState
import com.softcross.insuranceapp.domain.model.Make
import com.softcross.insuranceapp.domain.model.Province
import com.softcross.insuranceapp.domain.model.User
import com.softcross.insuranceapp.domain.repository.FirebaseRepository
import com.softcross.insuranceapp.domain.repository.VehicleAndLocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val vehicleAndLocationRepository: VehicleAndLocationRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {


    private val _makeState = mutableStateOf<ScreenState<List<Make>>>(ScreenState.Loading)
    val makeState: State<ScreenState<List<Make>>> = _makeState

    private val _addressState = mutableStateOf<ScreenState<List<Province>>>(ScreenState.Loading)
    val addressState: State<ScreenState<List<Province>>> = _addressState

    private val _userState = mutableStateOf(UserState(null,"", true))
    val userState: State<UserState> get() = _userState


    data class UserState(val user: User?, val errorMessage: String = "", val isLoading:Boolean = false)

    init {
        getMakes()
        getAllProvinces()
    }



    fun getUserWithID(userID: String) = viewModelScope.launch {
        try {
            val user = firebaseRepository.getUserDetailFromFirestore(userID)
            CurrentUser.setCurrentUser(user)
            _userState.value = UserState(user,"", false)
        } catch (e: Exception) {
            _userState.value = UserState(null,e.message ?: "An error occurred", false)
        }
    }



    private fun getMakes() = viewModelScope.launch {
        vehicleAndLocationRepository.getCarMakes().collectLatest { response ->
            when (response) {
                is NetworkResponseState.Success -> {
                    _makeState.value = ScreenState.Success(response.result)
                    AllMakes.setMakeList(response.result)
                }

                is NetworkResponseState.Error -> {
                    _makeState.value =
                        ScreenState.Error(response.exception.message ?: "An error occurred")
                }

                is NetworkResponseState.Loading -> {
                    _makeState.value = ScreenState.Loading
                }
            }
        }
    }

    private fun getAllProvinces() = viewModelScope.launch {
        vehicleAndLocationRepository.getAllProvinceAndDistricts().collect { response ->
            when (response) {
                is NetworkResponseState.Success -> {
                    _addressState.value = ScreenState.Success(response.result)
                    AllLocations.setLocations(response.result)
                }

                is NetworkResponseState.Error -> {
                    _addressState.value =
                        ScreenState.Error(response.exception.message ?: "An error")
                }

                is NetworkResponseState.Loading -> {
                    _addressState.value = ScreenState.Loading
                }
            }
        }
    }

}