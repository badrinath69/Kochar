package com.pivotalsoft.kochar.modal

import com.google.gson.annotations.SerializedName

data class GetAddressDataModel(
    @SerializedName("userid") val userid: String?=null,
    @SerializedName("fullname") val fullname: String?=null,
    @SerializedName("mobile") val mobile: String?=null,
    @SerializedName("password") val password: String?=null,
    @SerializedName("role") val role: String?=null,
    @SerializedName("status") val status: String?=null,
    @SerializedName("schoolcode") val schoolcode: String?=null,
    @SerializedName("address_id") val address_id: String?=null,
    @SerializedName("address") val address: String?=null,
    @SerializedName("city") val city: String?=null,
    @SerializedName("landmark") val landmark: String?=null,
    @SerializedName("state") val state: String?=null

)