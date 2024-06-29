package com.sercancelik.turkcellgygfinal.repositories

import com.sercancelik.turkcellgygfinal.models.response.Products
import com.sercancelik.turkcellgygfinal.network.ProductApi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(private val productApi: ProductApi) {

    suspend fun getProducts(page: Int, limit: Int) = productApi.getProducts(page, limit)

    suspend fun searchProducts(query: String): Response<Products> {
        return productApi.searchProducts(query)
    }


}
