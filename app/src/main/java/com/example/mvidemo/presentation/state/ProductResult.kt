package com.example.mvidemo.presentation.state

import com.example.assignment.ui.model.Products

sealed class ProductResult {
    object loading: ProductResult()
    data class Success(val products:List<Products>): ProductResult()
    data class Error(val message: String): ProductResult()
}