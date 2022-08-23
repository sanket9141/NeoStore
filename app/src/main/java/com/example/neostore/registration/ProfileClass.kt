package com.example.neostore.registration

data class ProfileClass(
    val access_token: String,
    val email: String,
    val first_name: String,
    val gender: String,
    val id: Int?,
    val is_active: Boolean,
    val last_name: String,
    val modified: String,
    val phone_no: Long,
    val role_id: Int,
    val username: String
)
