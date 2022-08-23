package com.example.neostore.mycart

data class AddToCartResponse(
    val `data`: Boolean,
    val message: String,
    val status: Int,
    val total_carts: Int,
    val user_msg: String
)