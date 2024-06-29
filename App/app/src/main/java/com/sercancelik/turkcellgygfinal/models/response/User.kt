package com.sercancelik.turkcellgygfinal.models.response

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val phone: String,
    val password: String,
    val image: String?,
    val token: String?,

)
