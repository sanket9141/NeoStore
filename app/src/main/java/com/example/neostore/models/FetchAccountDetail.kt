package com.example.neostore.models

import com.example.neostore.myaccount.Data

data class FetchAccountDetail(
    val `data`: AccountDetail,
    val status: Int
)

data class AccountDetail(
    val product_categories: List<ProductCategory>,
    val total_carts: Int,
    val total_orders: Int,
    val user_data: Data
)

data class ProductCategory(
    val created: String,
    val icon_image: String,
    val id: Int,
    val modified: String,
    val name: String
)

//data class UserData(
//    val access_token: String,
//    val country_id: Any,
//    val created: String,
//    val dob: String,
//    val email: String,
//    val first_name: String,
//    val gender: String,
//    val id: Int,
//    val is_active: Boolean,
//    val last_name: String,
//    val modified: String,
//    val phone_no: String,
//    val profile_pic: String,
//    val role_id: Int,
//    val username: String
//)