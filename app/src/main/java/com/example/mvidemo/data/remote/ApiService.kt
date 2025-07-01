
package com.example.mvidemo.data.remote

import com.example.assignment.ui.model.Products
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Products>
}
