package com.sercancelik.turkcellgygfinal.network

import com.sercancelik.turkcellgygfinal.models.Product
import com.sercancelik.turkcellgygfinal.models.response.Products
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {
    @GET("products")
    suspend fun getProducts(@Query("skip") page: Int, @Query("limit") limit: Int): Products

    @GET("products/{id}")
    suspend fun getSingleProduct(@Path("id") id: Long): Product

    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String): Response<Products>

}