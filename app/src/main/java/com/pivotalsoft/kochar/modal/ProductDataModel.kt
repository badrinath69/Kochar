package com.pivotalsoft.kochar.modal

import com.google.gson.annotations.SerializedName

class ProductDataModel(
    @SerializedName("productid") val productid: String,
    @SerializedName("productcode") val productcode: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("is_available") val is_available: String,
    @SerializedName("subcatid") val subcatid: String,
    @SerializedName("sxcode") val sxcode: String,
    @SerializedName("schoolcode") val schoolcode: String,
    @SerializedName("sizeid") val sizeid: String,
    @SerializedName("size") val size: String,
    @SerializedName("actual_price") val actual_price: String,
    @SerializedName("discount_price") val discount_price: String,
    @SerializedName("pgid") val pgid: String,
    @SerializedName("colorurl") val colorurl: String,
    @SerializedName("picurl") val picurl: String,
    @SerializedName("schoolid") val schoolid: String,
    @SerializedName("fullname") val fullname: String,
    @SerializedName("city") val city: String,
    @SerializedName("address") val address: String,
    @SerializedName("contactname") val contactname: String,
    @SerializedName("contactno") val contactno: String,
    @SerializedName("designation") val designation: String



)
