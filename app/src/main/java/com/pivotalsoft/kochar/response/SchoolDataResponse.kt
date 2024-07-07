package com.pivotalsoft.kochar.response

import com.google.gson.annotations.SerializedName
import com.pivotalsoft.kochar.userdetails.SchoolData

data class SchoolDataResponse(
    @SerializedName("schooldata") val schooldata: List<SchoolData>,
    @SerializedName("message") val message: String
)
