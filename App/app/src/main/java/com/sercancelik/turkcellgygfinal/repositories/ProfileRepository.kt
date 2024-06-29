package com.sercancelik.turkcellgygfinal.repositories

import com.sercancelik.turkcellgygfinal.models.response.User
import com.sercancelik.turkcellgygfinal.network.ProfileApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(private val profileApi: ProfileApi) {

    suspend fun getUser(id: Long): User {
        return profileApi.getUser(id)
    }

    suspend fun updateUser(id: Long, user: User): User? {
        return try {
            val response = profileApi.updateUser(id, user)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
