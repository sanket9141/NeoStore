package com.example.neostore.orderlist

data class OrderListResponse(
    val `data`: List<OrderListData>,
    val status: Int
)

data class OrderListData(
    val id : Number,
    val cost : Number,
    val created:Number
    )