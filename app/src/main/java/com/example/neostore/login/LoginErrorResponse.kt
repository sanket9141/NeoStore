package com.example.neostore.login

data class LoginErrorResponse(
    val message: String,
    val status: Int,
    val user_msg: String
)