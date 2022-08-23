package com.example.neostore.myaccount

data class InvalidData(
    val `data`: DataX,
    val message: String,
    val status: Int,
    val user_msg: String
)

data class DataX(
    val email: String,
    val first_name: String,
    val gender: String,
    val last_name: String,
    val phone_no: Long
)