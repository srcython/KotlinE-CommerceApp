package com.sercancelik.turkcellgygfinal.repositories

import androidx.lifecycle.LiveData
import com.sercancelik.turkcellgygfinal.data.ProductDao
import com.sercancelik.turkcellgygfinal.models.FavoriteProduct
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductDbRepository @Inject constructor(
    private val productDao: ProductDao
) {
    fun getFavoriteProduct(productId: Long): LiveData<FavoriteProduct?> {
        return productDao.getFavoriteProduct(productId)
    }

    suspend fun addFavoriteProduct(favoriteProduct: FavoriteProduct) {
        productDao.addFavoriteProduct(favoriteProduct)
    }

    suspend fun removeFavoriteProduct(productId: Long) {
        productDao.removeFavoriteProductById(productId)
    }

    fun getAllFavoriteProducts(): LiveData<List<FavoriteProduct>> {
        return productDao.getAllFavoriteProducts()
    }
}
