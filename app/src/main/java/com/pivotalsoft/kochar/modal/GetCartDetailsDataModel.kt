package com.pivotalsoft.kochar.modal

import com.google.gson.annotations.SerializedName

data class GetCartDetailsDataModel(
    @SerializedName("cartitemid") val cartitemid: String,
    @SerializedName("product_id") val product_id: String,
    @SerializedName("no_units") val no_units: String? = null,
    @SerializedName("user_id") val user_id: String,
    @SerializedName("added_on") val added_on: String? = null,
    @SerializedName("total_price") val total_price: String? = null,
    @SerializedName("actual_price") val actual_price: String? = null,
   // @SerializedName("productid") val productid: String,
    @SerializedName("productcode") val productcode: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("is_available") val is_available: String,
    @SerializedName("subcatid") val subcatid: String,
    @SerializedName("sxcode") val sxcode: String,
    @SerializedName("schoolcode") val schoolcode: String,

)