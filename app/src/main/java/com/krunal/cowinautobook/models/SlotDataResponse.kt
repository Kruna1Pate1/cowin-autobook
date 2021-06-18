package com.krunal.cowinautobook.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SloatDataResponse(

    @SerializedName("sessions")
    val sessions: ArrayList<Sessions>
)

data class Sessions(

    @SerializedName("center_id")
    val center_id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("state_name")
    val state_name: String,

    @SerializedName("district_name")
    val district_name: String,

    @SerializedName("block_name")
    val block_name: String,

    @SerializedName("pincode")
    val pincode: Int,

    @SerializedName("from")
    val from: String,

    @SerializedName("to")
    val to: String,

    @SerializedName("lat")
    val lat: Int,

    @SerializedName("long")
    val long: Int,

    @SerializedName("fee_type")
    val fee_type: String,

    @SerializedName("session_id")
    val session_id: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("available_capacity")
    val available_capacity: Int,

    @SerializedName("available_capacity_dose1")
    val available_capacity_dose1: Int,

    @SerializedName("available_capacity_dose2")
    val available_capacity_dose2: Int,

    @SerializedName("fee")
    val fee: String,

    @SerializedName("min_age_limit")
    val min_age_limit: Int,

    @SerializedName("vaccine")
    val vaccine: String,

    @SerializedName("sloats")
    val sloats: Array<String>
)