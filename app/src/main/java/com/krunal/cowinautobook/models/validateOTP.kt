package com.krunal.cowinautobook.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.krunal.cowinautobook.utility.Constants

data class validateOTP constructor(
    val txnId: String,
    val otp: String
)
