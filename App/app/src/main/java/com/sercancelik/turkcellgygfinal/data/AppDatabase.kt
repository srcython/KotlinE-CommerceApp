package com.sercancelik.turkcellgygfinal.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sercancelik.turkcellgygfinal.models.FavoriteProduct

@Database(entities = [FavoriteProduct::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
