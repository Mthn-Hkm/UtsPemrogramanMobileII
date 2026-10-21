package com.example.utspemrogramanmobile.model

import java.io.Serializable

data class Review(
    val rating: Int,
    val comment: String,
    val date: String,
    val reviewerName: String,
    val reviewerEmail: String
) : Serializable
