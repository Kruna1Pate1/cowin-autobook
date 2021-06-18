package com.krunal.cowinautobook.adapter

interface BeneficiaryCbListener {
    fun onChecked(beneficiaryId: String)
    fun onUnchecked(beneficiaryId: String)
    fun isExist(beneficiaryId: String): Boolean
}