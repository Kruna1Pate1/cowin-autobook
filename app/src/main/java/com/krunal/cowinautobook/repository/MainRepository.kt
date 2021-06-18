package com.krunal.cowinautobook.repository

import com.krunal.cowinautobook.models.*
import com.krunal.cowinautobook.network.ApiServiceImpl
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiServiceImpl: ApiServiceImpl
) {
    suspend fun getSloatDetails(
        pincode: String,
        date: String
    ) = apiServiceImpl.getSloatDetails(pincode, date)

    suspend fun generateOtp(
        generateOtp: GenerateOTP
    ) = apiServiceImpl.generateOtp(generateOtp)

    suspend fun validateOtp(
        validateOTP: validateOTP
    ) = apiServiceImpl.validateOtp(validateOTP)

    suspend fun getBeneficiary(
        authToken: String
    ) = apiServiceImpl.getBeneficiary(authToken)

    suspend fun getCalendar(
        pincode: String,
        date: String,
        authToken: String
    ) = apiServiceImpl.getCalendar(pincode, date, authToken)

    suspend fun schedule(
        schedule: Schedule,
        authToken: String
    ): Response<ScheduleResponse> = apiServiceImpl.schedule(schedule, authToken)

    suspend fun getRecaptcha(
        authToken: String
    ): Response<RecaptchaResponse> = apiServiceImpl.getRecaptcha(authToken)
}