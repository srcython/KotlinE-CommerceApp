package com.sercancelik.turkcellgygfinal.models.request

import com.sercancelik.turkcellgygfinal.models.Product

data class CartRequest(
    val userId: Long,
    val products: List<Product>
)