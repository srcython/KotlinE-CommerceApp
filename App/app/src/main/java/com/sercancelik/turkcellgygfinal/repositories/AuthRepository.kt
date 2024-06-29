package com.sercancelik.turkcellgygfinal.repositories

import com.sercancelik.turkcellgygfinal.models.request.AuthRequest
import com.sercancelik.turkcellgygfinal.network.AuthApi
import com.sercancelik.turkcellgygfinal.util.KeystoreManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val keystoreManager: KeystoreManager
) {

    fun getToken(): String? {
        return keystoreManager.getToken()
    }

    private fun saveToken(token: String) {
        keystoreManager.encryptAndStoreToken(token)
    }

    fun clearToken() {
        keystoreManager.clearToken()
    }

    suspend fun login(username: String, password: String): String? {
        val response = authApi.login(AuthRequest(username, password))
        val token = response.token
        token?.let {
            saveToken(it)
        }
        return token
    }
}
