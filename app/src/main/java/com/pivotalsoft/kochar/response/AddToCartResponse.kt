package com.pivotalsoft.kochar.response

import com.google.gson.annotations.SerializedName

data class AddToCartResponse(
    @SerializedName("status") val message: String
)