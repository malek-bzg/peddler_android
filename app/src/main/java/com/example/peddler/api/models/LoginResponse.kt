package com.example.peddler.api.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token")
    val token: String?,
    val user: User
)