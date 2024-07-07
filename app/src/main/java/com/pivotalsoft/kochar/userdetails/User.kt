package com.pivotalsoft.kochar.userdetails

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("userid") val userid: String,
    @SerializedName("fullname") val fullname: String,
    @SerializedName("mobile") val mobile: String,
    @SerializedName("password") val password: String,
    @SerializedName("role") val role: String,
    @SerializedName("status") val status: String,
    @SerializedName("schoolcode") val schoolcode: String
)