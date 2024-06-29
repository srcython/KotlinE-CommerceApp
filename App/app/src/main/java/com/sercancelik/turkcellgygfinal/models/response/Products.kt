package com.sercancelik.turkcellgygfinal.models.response

import com.sercancelik.turkcellgygfinal.models.Product

data class Products(
    val products: List<Product>,
    val total: Long,
    val skip: Long,
    val limit: Long,
)

