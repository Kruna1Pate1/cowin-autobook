package com.krunal.cowinautobook.models

import com.google.gson.annotations.SerializedName

data class RecaptchaResponse(

    @SerializedName("captcha")
    val captcha: String
)
