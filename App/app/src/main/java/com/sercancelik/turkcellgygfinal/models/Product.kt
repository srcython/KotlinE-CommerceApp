package com.sercancelik.turkcellgygfinal.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Long,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val images: List<String>,
    val thumbnail: String,
    val quantity: Int,
    val reviews: List<Review>,
) : Parcelable

@Parcelize
data class Review(
    val rating: Long,
    val comment: String,
    val date: String,
    val reviewerName: String,
    val reviewerEmail: String,
) : Parcelable