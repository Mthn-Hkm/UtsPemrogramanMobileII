package com.example.utspemrogramanmobile.model

import java.io.Serializable

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val stock: Int,
    val availabilityStatus: String,
    val tags: List<String>,
    val dimensions: Dimensions,
    val reviews: List<Review>,
    val rating: Double,
    val images: List<String>,
    val thumbnail: String
) : Serializable
