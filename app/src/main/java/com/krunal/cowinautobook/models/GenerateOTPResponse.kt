package com.krunal.cowinautobook.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GenerateOTPResponse constructor(
    @SerializedName("txnId")
    val txnId: String
)
