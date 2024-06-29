package com.sercancelik.turkcellgygfinal.models

data class Cart(
    val id: Long,
    val products: List<Product>,
    val total: Double,
    val discountedTotal: Double,
    val userId: Long,
    val totalProducts: Long,
    val totalQuantity: Long,
    var isExpanded: Boolean = false
)