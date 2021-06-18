package com.krunal.cowinautobook.utility

import java.lang.StringBuilder
import java.math.BigInteger
import java.security.MessageDigest

object Constants {

    private val TAG = Constants::class.java.simpleName

    const val BASE_URL = "https://cdn-api.co-vin.in/api/v2/"


    const val SECRET = "U2FsdGVkX192cGNpIRD0G92OHREIZe/1r4zeK9Ai852OEbU7YpNszHKrvMv+muPvbDWN2QPTRm7dsgV+0UoxxA=="


    const val SHARED_PREFS = "data"
    const val PIN = "pinCode"
    const val PHONE = "phoneNum"
    const val TXN_ID = "txnId"
    const val AUTH_TOKEN = "authToken"
    const val LOGIN = "isLoggedIn"
    const val DOSE = "dose"
    const val BENFICIRY = "beneficiaries"
    const val SLOT_TIME = "FORENOON"


    //Custom header requires for sending requests
    private const val USER_AGENT =
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36"
    private const val ORIGIN = "https://selfregistration.cowin.gov.in/"
    private const val REFERER = "https://selfregistration.cowin.gov.in/"


    fun getHeaders(): Map<String, String> {
        val headers: MutableMap<String, String> = HashMap()
        headers["User-Agent"] = USER_AGENT
        headers["Origin"] = ORIGIN
        headers["Referer"] = REFERER
        return headers
    }

    fun sha256Convertor(str: String): String {
        val bytes = str.toString().toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }

    fun getOtpFromMsg(msg: String): String = sha256Convertor(msg.substring(37, 43))

}