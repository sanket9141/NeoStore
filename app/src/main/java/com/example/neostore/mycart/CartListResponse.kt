package com.example.neostore.mycart

data class CartListResponse(
    val count: Int,
    val `data`: List<CartData>,
    val status: Int,
    val total: Double
)
data class CartData(
    val id: Int,
    val product: Product,
    val product_id: Int,
    val quantity: Int
)
data class Product(
    val cost: Int,
    val id: Int,
    val name: String,
    val product_category: String,
    val product_images: String,
    val sub_total: Int
)

data class CartDeleteResponse(
    val `data`: Boolean,
    val message: String,
    val status: Int,
    val total_carts: Int,
    val user_msg: String
)

data class CartEditResponse(
    val `data`: Boolean,
    val message: String,
    val status: Int,
    val total_carts: Int,
    val user_msg: String
)