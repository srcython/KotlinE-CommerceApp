package com.sercancelik.turkcellgygfinal.di

import android.content.Context
import androidx.room.Room
import com.sercancelik.turkcellgygfinal.data.AppDatabase
import com.sercancelik.turkcellgygfinal.data.ProductDao
import com.sercancelik.turkcellgygfinal.repositories.ProductDbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    fun provideProductRepository(productDao: ProductDao): ProductDbRepository {
        return ProductDbRepository(productDao)
    }
}
