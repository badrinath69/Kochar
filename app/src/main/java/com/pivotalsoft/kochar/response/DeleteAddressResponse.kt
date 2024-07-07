package com.pivotalsoft.kochar.response

import com.google.gson.annotations.SerializedName

data class DeleteAddressResponse(
    @SerializedName("status") val status: String
)