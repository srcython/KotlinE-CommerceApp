package com.sercancelik.turkcellgygfinal.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "favorite_products")
data class FavoriteProduct(
    @PrimaryKey val productId: Long,
    val title: String,
    val thumbnail: String,
    val createdAt: Long

)
