package com.krunal.cowinautobook.models

import com.google.gson.annotations.SerializedName

data class ScheduleResponse(
    @SerializedName("appointment_id")
    val appointment_id: String
)
