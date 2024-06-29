package com.sercancelik.turkcellgygfinal.network

import com.sercancelik.turkcellgygfinal.models.Cart
import com.sercancelik.turkcellgygfinal.models.request.CartRequest
import com.sercancelik.turkcellgygfinal.models.response.Carts
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CartApi {
    @POST("carts/add")
    suspend fun addToCart(@Body cartRequest: CartRequest): Response<Cart>

    @GET("carts/user/{userId}")
    suspend fun getUserCarts(@Path("userId") userId: Long): Carts
}