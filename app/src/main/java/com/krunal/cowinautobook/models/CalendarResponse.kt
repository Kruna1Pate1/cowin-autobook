package com.krunal.cowinautobook.models

import com.google.gson.annotations.SerializedName

data class CalendarResponse(

    @SerializedName("centers")
    val centers: List<Centers>
)

data class Centers(

    @SerializedName("center_id")
    val center_id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("state_name")
    val state_name: String,

    @SerializedName("district_na")
    val district_name: String,

    @SerializedName("block_name")
    val block_name: String,

    @SerializedName("pincode")
    val pincode: String,

    @SerializedName("lat")
    val lat: Float,

    @SerializedName("long")
    val long: Float,

    @SerializedName("from")
    val from: String,

    @SerializedName("to")
    val to: String,

    @SerializedName("fee_type")
    val fee_type: String,

    @SerializedName("vaccine_fees")
    val vaccine_fees: VaccineFees,

    @SerializedName("sessions")
    val sessions: List<Sessions>
)

data class VaccineFees(
    @SerializedName("vaccine")
    val vaccine: String,

    @SerializedName("fee")
    val fee: String
)