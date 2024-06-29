package com.sercancelik.turkcellgygfinal.repositories

import com.sercancelik.turkcellgygfinal.models.response.Category
import com.sercancelik.turkcellgygfinal.models.response.Products
import com.sercancelik.turkcellgygfinal.network.CategoryApi
import retrofit2.Response
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryApi: CategoryApi) {

    suspend fun getCategories(): List<Category> = categoryApi.getCategories()

    suspend fun getProductsByCategorySlug(categorySlug: String): Response<Products> {
        return categoryApi.getProductsByCategorySlug(categorySlug)
    }

}