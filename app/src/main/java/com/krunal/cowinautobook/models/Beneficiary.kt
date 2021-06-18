package com.krunal.cowinautobook.models

data class Beneficiary(
    val name: String,
    val beeficiaryId: String,
    val photoId: String,
    var isSelected: Boolean = false
)
