package com.example.mvidemo.data.repository

import com.example.assignment.ui.model.Products
import com.example.mvidemo.data.remote.ApiService
import com.example.mvidemo.domain.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ApiService
) : ProductRepository {
    override suspend fun getProducts(): Result<List<Products>>
    {
        return try {
            val response = api.getProducts()
            Result.success(response)
        }
        catch (e:Exception)
        {
            Result.failure(e)
        }
    }
}
