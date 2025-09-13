package com.example.foodorderingapp.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorderingapp.common.ResultState
import com.example.foodorderingapp.data.models.UserData
import com.example.foodorderingapp.domain.useCases.createUserUseCase
import com.example.foodorderingapp.domain.useCases.loginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUserUseCase: loginUserUseCase,
    private val createUserUseCase: createUserUseCase
) : ViewModel() {

    // UI State (exposed to Compose)
    private var _signUpScreenState = MutableStateFlow(SignupStatee())
    val signUpScreenState = _signUpScreenState.asStateFlow()

    private var _loginScreenState = MutableStateFlow(LoginStatee())
    val loginScreenState = _loginScreenState.asStateFlow()


    // --- Login Function ---
    fun loginUser(userData: UserData) {
        viewModelScope.launch {
            loginUserUseCase.loginUser(userData).collect {
                when (it) {
                    is ResultState.Error -> {
                        _loginScreenState.value = _loginScreenState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    ResultState.Loading -> {
                        _loginScreenState.value = _loginScreenState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _loginScreenState.value = _loginScreenState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    // --- Register Function ---
    fun registerUser(userData: UserData) {
        viewModelScope.launch {
            createUserUseCase.createUser(userData).collect {
                when (it) {
                    is ResultState.Error -> {
                        _signUpScreenState.value = _signUpScreenState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }

                    ResultState.Loading -> {
                        _signUpScreenState.value = _signUpScreenState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _signUpScreenState.value = _signUpScreenState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }


                }
            }
        }
    }
}


// --- UI State Model ---
data class SignupStatee(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)


data class LoginStatee(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)
