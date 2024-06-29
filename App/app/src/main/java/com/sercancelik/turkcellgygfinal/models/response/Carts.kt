package com.sercancelik.turkcellgygfinal.models.response

import com.sercancelik.turkcellgygfinal.models.Cart

data class Carts(
    val carts: List<Cart>,
    val total: Long,
    val skip: Long,
    val limit: Long,
)
