package com.krunal.cowinautobook.network

import com.krunal.cowinautobook.models.*
import retrofit2.Response
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(
    private val apiService: ApiService
    ) {
    suspend fun getSloatDetails(
        pincode: String,
        date: String
    ): Response<SloatDataResponse> = apiService.getSloatDetails(pincode, date)

    suspend fun generateOtp(
        generateOtp: GenerateOTP
    ): Response<GenerateOTPResponse> = apiService.generateOtp(generateOtp)

    suspend fun validateOtp(
        validateOtp: validateOTP
    ): Response<validateOTPResponse> = apiService.validateOtp(validateOtp)

    suspend fun getBeneficiary(
        authToken: String
    ): Response<BeneficiaryResponse> = apiService.getBeneficiary(authToken)

    suspend fun getCalendar(
        pincode: String,
        date: String,
        authToken: String
    ): Response<CalendarResponse> = apiService.getCalendar(pincode, date, authToken)

    suspend fun schedule(
        schedule: Schedule,
        authToken: String
    ): Response<ScheduleResponse> = apiService.schedule(schedule, authToken)

    suspend fun getRecaptcha(
        authToken: String
    ): Response<RecaptchaResponse> = apiService.getRecaptcha(authToken)
}