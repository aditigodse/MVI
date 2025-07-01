
package com.example.mvidemo.presentation.state

import com.example.assignment.ui.model.Products

data class ProductsState(
    val isLoading: Boolean = false,
    val products: List<Products> = emptyList(),
    val error: String? = null
)
