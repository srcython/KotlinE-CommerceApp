package com.sercancelik.turkcellgygfinal.util

import android.util.Base64
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JwtUtils @Inject constructor() {
    private fun decodeJWT(token: String): JSONObject {
        val parts = token.split(".")
        if (parts.size != 3) {
            throw IllegalArgumentException("Invalid JWT token.")
        }
        val payload = parts[1]
        val decodedBytes = Base64.decode(payload, Base64.URL_SAFE)
        val decodedString = String(decodedBytes)
        return JSONObject(decodedString)
    }

    fun getUserIdFromToken(token: String): Long {
        val decodedJWT = decodeJWT(token)
        return decodedJWT.getLong("id")
    }

    fun getUsernameFromToken(token: String): String {
        val decodedJWT = decodeJWT(token)
        return decodedJWT.getString("firstName")
    }

    fun getUserPhotoFromToken(token: String): String {
        val decodedJWT = decodeJWT(token)
        return decodedJWT.getString("image")

    }
}
