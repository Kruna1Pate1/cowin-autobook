package com.krunal.cowinautobook.models

import com.google.gson.annotations.Expose
import com.krunal.cowinautobook.utility.Constants

data class GenerateOTP(
    val mobile: String,
    val secret: String = Constants.SECRET
)