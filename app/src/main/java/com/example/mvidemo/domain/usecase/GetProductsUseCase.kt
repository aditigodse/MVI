package com.example.mvidemo.domain.usecase

import com.example.assignment.ui.model.Products

interface GetProductsUseCase {
    suspend operator fun invoke(): Result<List<Products>>
}