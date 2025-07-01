package com.example.assignment.ui.model

data class Products(
    val image: String = "",

    val price: Double = 0.0,

    val rating: Rating,

    val description: String = "",

    val id: Int = 0,

    val title: String = "",

    val category: String = ""
)
