package com.pivotalsoft.kochar.response

import com.google.gson.annotations.SerializedName

data class SignupResponse(

    @SerializedName("status") val status: String

)
