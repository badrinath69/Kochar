package com.pivotalsoft.kochar.response

import com.google.gson.annotations.SerializedName

data class AddAddressResponse(
    @SerializedName("message") val message: String
)
