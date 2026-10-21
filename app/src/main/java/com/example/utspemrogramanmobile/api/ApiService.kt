package com.example.utspemrogramanmobile.api

import com.example.utspemrogramanmobile.model.ProductResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    fun getProducts(
        @Query("limit") limit: Int = 25,
        @Query("select") select: String = "id,title,description,category,price,stock,availabilityStatus,tags,dimensions,reviews,rating,images,thumbnail"
    ): Call<ProductResponse>
}
