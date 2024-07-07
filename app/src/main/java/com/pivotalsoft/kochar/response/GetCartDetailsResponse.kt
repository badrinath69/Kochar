package com.pivotalsoft.kochar.response

import com.google.gson.annotations.SerializedName
import com.pivotalsoft.kochar.modal.GetAddressDataModel
import com.pivotalsoft.kochar.modal.GetCartDetailsDataModel

data class GetCartDetailsResponse(
    @SerializedName("cartlistdata") val cartlistdata: List<GetCartDetailsDataModel>,
    @SerializedName("message") val message: String
)
