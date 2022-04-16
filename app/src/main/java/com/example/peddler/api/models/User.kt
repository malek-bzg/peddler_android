package com.example.peddler.api.models

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val address: String,
    val cin: String,
    val password: String,
    val ConfirmPassword: String,
    //val profilePicture:String,
    //val role:String,
)