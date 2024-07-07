package com.pivotalsoft.kochar.response

import com.google.gson.annotations.SerializedName
import com.pivotalsoft.kochar.modal.GetAddressDataModel
import com.pivotalsoft.kochar.modal.ProductDataModel

data class GetAddressResponse(
    @SerializedName("address") val address: List<GetAddressDataModel>,
    @SerializedName("message") val message: String
)
