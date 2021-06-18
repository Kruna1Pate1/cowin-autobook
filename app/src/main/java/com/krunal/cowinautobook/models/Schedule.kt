package com.krunal.cowinautobook.models

import com.google.gson.annotations.SerializedName

data class Schedule(
    @SerializedName("dose")
    val dose: Int,

    @SerializedName("session_id")
    val sessionId: String,

    @SerializedName("center_id")
    val centerId: Int,

    @SerializedName("slot")
    val slot: String,

    @SerializedName("beneficiaries")
    val beneficiaries: List<String>
)
