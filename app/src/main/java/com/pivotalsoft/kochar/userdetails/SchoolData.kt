package com.pivotalsoft.kochar.userdetails

import com.google.gson.annotations.SerializedName

data class SchoolData(
    @SerializedName("schoolid") val schoolid: String,
    @SerializedName("fullname") val fullname: String,
    @SerializedName("city") val city: String,
    @SerializedName("address") val address: String,
    @SerializedName("contactname") val contactname: String,
    @SerializedName("contactno") val contactno: String,
    @SerializedName("designation") val designation: String

){
    override fun toString(): String {
        return fullname
    }
}

