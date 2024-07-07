package com.pivotalsoft.kochar.response

import com.google.gson.annotations.SerializedName

data class updateCartItemsResponse(
    @SerializedName("status") val status: String
)