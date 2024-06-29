package com.sercancelik.turkcellgygfinal.repositories

import android.util.Log
import com.sercancelik.turkcellgygfinal.models.Cart
import com.sercancelik.turkcellgygfinal.models.Product
import com.sercancelik.turkcellgygfinal.models.request.CartRequest
import com.sercancelik.turkcellgygfinal.network.CartApi
import retrofit2.Response
import javax.inject.Inject

class CartRepository @Inject constructor(private val cartApi: CartApi) {

    suspend fun addToCart(userId: Long, products: List<Product>): Response<Cart> {
        val cartRequest = CartRequest(
            userId = userId,
            products = products
        )
        return cartApi.addToCart(cartRequest)
    }

    suspend fun getUserCarts(userId: Long): List<Cart> {
        val response = cartApi.getUserCarts(userId)
        return response.carts
    }
}
