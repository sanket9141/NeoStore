package com.example.neostore.orderdetail

data class OrderDetailResponse(
    val `data`: OrderdetailData,
    val status: Int
)

data class OrderdetailData(
    val address: String,
    val cost: Int,
    val id: Int,
    val order_details: List<OrderDetail>
)

data class OrderDetail(
    val id: Int,
    val order_id: Int,
    val prod_cat_name: String,
    val prod_image: String,
    val prod_name: String,
    val product_id: Int,
    val quantity: Int,
    val total: Int
)