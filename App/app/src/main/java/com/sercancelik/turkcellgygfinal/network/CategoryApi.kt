package com.sercancelik.turkcellgygfinal.network

import com.sercancelik.turkcellgygfinal.models.response.Category
import com.sercancelik.turkcellgygfinal.models.response.Products
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApi {
    @GET("products/categories")
    suspend fun getCategories(): List<Category>

    @GET("products/category/{slug}")
    suspend fun getProductsByCategorySlug(@Path("slug") categorySlug: String): Response<Products>
}