package com.sercancelik.turkcellgygfinal.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sercancelik.turkcellgygfinal.models.UserAuthState
import com.sercancelik.turkcellgygfinal.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<UserAuthState>(UserAuthState.Idle)
    val authState: StateFlow<UserAuthState> = _authState

    init {
        checkToken()
    }

    private fun checkToken() {
        viewModelScope.launch {
            val token = authRepository.getToken()
            _authState.value = if (token != null) {
                UserAuthState.Success(token)
            } else {
                UserAuthState.Idle
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = UserAuthState.Loading
            try {
                val token = authRepository.login(username, password)
                if (token != null) {
                    _authState.value = UserAuthState.Success(token)
                } else {
                    _authState.value = UserAuthState.Error("Failed to retrieve token")
                }
            } catch (e: HttpException) {
                if (e.code() == 400) {
                    _authState.value = UserAuthState.InvalidCredentials
                } else {
                    _authState.value = UserAuthState.Error(e.message ?: "An unknown error occurred")
                }
            } catch (e: Exception) {
                val errorMessage = e.message ?: "An unknown error occurred"
                _authState.value = UserAuthState.Error(errorMessage)
            }
        }
    }
}
