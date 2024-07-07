package com.pivotalsoft.kochar.response

import com.google.gson.annotations.SerializedName

data class uploadcgstallResponse(
    @SerializedName("status") val status: String
)