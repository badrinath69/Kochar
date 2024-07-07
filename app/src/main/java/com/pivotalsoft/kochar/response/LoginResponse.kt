package com.pivotalsoft.kochar.response

import com.google.gson.annotations.SerializedName
import com.pivotalsoft.kochar.userdetails.User

data class LoginResponse(@SerializedName("userdata") val userdata : List<User>,
                         @SerializedName("message") val message : String)