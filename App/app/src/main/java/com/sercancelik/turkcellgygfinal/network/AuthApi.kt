package com.sercancelik.turkcellgygfinal.network

import com.sercancelik.turkcellgygfinal.models.request.AuthRequest
import com.sercancelik.turkcellgygfinal.models.response.User
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: AuthRequest): User
}
