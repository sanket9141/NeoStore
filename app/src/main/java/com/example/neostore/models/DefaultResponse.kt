package com.example.neostore.models

import com.example.neostore.myaccount.Data

data class DefaultResponse(
    val `data`: Data,
    val message: String,
    val status: Int,
    val user_msg: String
)