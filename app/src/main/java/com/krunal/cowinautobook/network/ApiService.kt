package com.krunal.cowinautobook.network

import com.krunal.cowinautobook.models.*
import com.krunal.cowinautobook.utility.Constants
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("appointment/sessions/public/findByPin")
    suspend fun getSloatDetails(
        @Query("pincode") pincode: String,
        @Query("date") date: String,
    ): Response<SloatDataResponse>

    @POST("auth/generateMobileOTP")
    suspend fun generateOtp(
        @Body generateOtp: GenerateOTP,
        @HeaderMap headers: Map<String, String> = Constants.getHeaders()
    ): Response<GenerateOTPResponse>

    @POST("auth/validateMobileOtp")
    suspend fun validateOtp(
        @Body validateOTP: validateOTP,
        @HeaderMap headers: Map<String, String> = Constants.getHeaders()
    ): Response<validateOTPResponse>

    @GET("appointment/beneficiaries")
    suspend fun getBeneficiary(
        @Header("Authorization") authToken: String,
        @HeaderMap headers: Map<String, String> = Constants.getHeaders()
    ): Response<BeneficiaryResponse>

    @GET("appointment/sessions/calendarByPin")
    suspend fun getCalendar(
        @Query("pincode") pincode: String,
        @Query("date") date: String,
        @Header("Authorization") authToken: String,
        @HeaderMap headers: Map<String, String> = Constants.getHeaders()
    ): Response<CalendarResponse>

    @POST("appointment/schedule")
    suspend fun schedule(
        @Body schedule: Schedule,
        @Header("Authorization") authToken: String,
        @HeaderMap headers: Map<String, String> = Constants.getHeaders()
    ): Response<ScheduleResponse>

    @POST("auth/getRecaptcha")
    suspend fun getRecaptcha(
        @Header("Authorization") authToken: String,
        @HeaderMap headers: Map<String, String> = Constants.getHeaders()
    ): Response<RecaptchaResponse>
}