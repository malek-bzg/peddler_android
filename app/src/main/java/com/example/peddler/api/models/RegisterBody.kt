package com.example.peddler.api.models

data class RegisterBody(
    val firstName: String,
    val lastName: String,
    val cin: String,
    val email: String,
    val address: String,
    val password: String
)