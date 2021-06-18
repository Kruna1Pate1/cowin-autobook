package com.krunal.cowinautobook.models

import com.google.gson.annotations.SerializedName

data class BeneficiaryResponse(
    @SerializedName("beneficiaries")
    val beneficiaries: List<Beneficiaries>
)

data class Beneficiaries(

    @SerializedName("beneficiary_reference_id")
    val beneficiary_reference_id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("birth_year")
    val birth_year: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("mobile_number")
    val mobile_number: String,

    @SerializedName("photo_id_type")
    val photo_id_type: String,

    @SerializedName("photo_id_number")
    val photo_id_number: String,

    @SerializedName("comorbidity_ind")
    val comorbidity_ind: String,

    @SerializedName("vaccination_status")
    val vaccination_status: String,

    @SerializedName("vaccine")
    val vaccine: String,

    @SerializedName("dose1_date")
    val dose1_date: String,

    @SerializedName("dose2_date")
    val dose2_date: String,

    @SerializedName("appointments")
    val appointments: List<Appointments>
)

data class Appointments(

    @SerializedName("appointment_id")
    val appointment_id: String,

    @SerializedName("center_id")
    val center_id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("state_name")
    val state_name: String,

    @SerializedName("district_name")
    val district_name: String,

    @SerializedName("block_name")
    val block_name: String,

    @SerializedName("from")
    val from: String,

    @SerializedName("to")
    val to: String,

    @SerializedName("dose")
    val dose: Int,

    @SerializedName("session_id")
    val session_id: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("slot")
    val slot: String
)
