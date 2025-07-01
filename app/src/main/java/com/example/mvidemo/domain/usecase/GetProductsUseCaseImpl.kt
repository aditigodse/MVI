package com.example.mvidemo.domain.usecase

import com.example.assignment.ui.model.Products
import com.example.mvidemo.domain.ProductRepository

class GetProductsUseCaseImpl(private val repository: ProductRepository):
    GetProductsUseCase {
    override suspend fun invoke(): Result<List<Products>> {
        return repository.getProducts()
    }
}
