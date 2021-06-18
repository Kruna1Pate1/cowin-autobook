package com.krunal.cowinautobook.models

import com.google.gson.annotations.SerializedName

data class validateOTPResponse(

    @SerializedName("token")
    val token: String
)
