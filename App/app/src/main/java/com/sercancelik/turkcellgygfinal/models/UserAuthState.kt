package com.sercancelik.turkcellgygfinal.models

sealed class UserAuthState {
    data object Idle : UserAuthState()
    data object Loading : UserAuthState()
    data class Success(val response: String) : UserAuthState()
    data class Error(val message: String) : UserAuthState()
    data object InvalidCredentials : UserAuthState()

}
