package com.pivotalsoft.kochar.response

import com.google.gson.annotations.SerializedName
import com.pivotalsoft.kochar.modal.ProductDataModel
import com.pivotalsoft.kochar.userdetails.SchoolData

class ProductDetailsResponse(
    @SerializedName("productdata") val productdata: List<ProductDataModel>,
    @SerializedName("message") val message: String
)