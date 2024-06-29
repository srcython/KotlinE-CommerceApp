package com.sercancelik.turkcellgygfinal.network

import com.sercancelik.turkcellgygfinal.models.response.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProfileApi {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Long): User

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: Long, @Body user: User): Response<User>
}
