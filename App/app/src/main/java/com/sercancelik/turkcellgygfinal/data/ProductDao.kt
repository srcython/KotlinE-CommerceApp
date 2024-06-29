package com.sercancelik.turkcellgygfinal.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sercancelik.turkcellgygfinal.models.FavoriteProduct

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteProduct(favoriteProduct: FavoriteProduct)

    @Query("DELETE FROM favorite_products WHERE productId = :productId")
    suspend fun removeFavoriteProductById(productId: Long)

    @Query("SELECT * FROM favorite_products WHERE productId = :productId")
    fun getFavoriteProduct(productId: Long): LiveData<FavoriteProduct?>

    @Query("SELECT * FROM favorite_products ORDER BY createdAt desc")
    fun getAllFavoriteProducts(): LiveData<List<FavoriteProduct>>
}
