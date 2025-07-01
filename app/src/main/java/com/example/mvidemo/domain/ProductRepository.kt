package com.example.mvidemo.domain

import com.example.assignment.ui.model.Products

interface ProductRepository {
    suspend fun getProducts(): Result<List<Products>>
}