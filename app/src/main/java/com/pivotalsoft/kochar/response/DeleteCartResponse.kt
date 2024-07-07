package com.pivotalsoft.kochar.response

import com.google.gson.annotations.SerializedName

data class DeleteCartResponse(
    @SerializedName("status") val status: String
)
