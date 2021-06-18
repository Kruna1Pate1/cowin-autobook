package com.krunal.cowinautobook.pref

import android.content.SharedPreferences
import com.krunal.cowinautobook.utility.Constants
import javax.inject.Inject

class SharedPrefs @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {

    private val editor = sharedPreferences.edit()

    // Initializing pinCode
    private var pinCode = sharedPreferences.getString(Constants.PIN, "")

    fun setPhoneNum(number: String) {
        if (getPhoneNum() != number) setLoginStatus(false)
        editor.putString(Constants.PHONE, number)
        editor.apply()
    }

    fun getPhoneNum(): String = sharedPreferences.getString(Constants.PHONE, "").toString()


    fun setPinCode(pin: String) {
        this.pinCode = pin
        editor.putString(Constants.PIN, pin)
        editor.apply()
    }

    // Directly returning pincode beacuse of often use
    fun getPinCode(): String = pinCode!!


    fun setTxnId(txnId: String) {
        editor.putString(Constants.TXN_ID, txnId)
        editor.apply()
    }

    fun getTxnId(): String = sharedPreferences.getString(Constants.TXN_ID, "").toString()


    fun setToken(token: String) {
        editor.putString(Constants.AUTH_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String = sharedPreferences.getString(Constants.AUTH_TOKEN, "").toString()

    fun setLoginStatus(status: Boolean) {
        editor.putBoolean(Constants.LOGIN, status)
        editor.apply()
    }

    fun getLoginStatus(): Boolean = sharedPreferences.getBoolean(Constants.LOGIN, false)

    fun setDose(dose: Int) {
        editor.putInt(Constants.DOSE, dose)
        editor.apply()
    }

    fun getDose(): Int = sharedPreferences.getInt(Constants.DOSE, 1)

    fun setBenificiary(benificiaries: List<String>) {
        editor.putStringSet(Constants.BENFICIRY, benificiaries.toSet())
        editor.apply()
    }

    fun getBenificiary(): List<String>? = sharedPreferences.getStringSet(Constants.BENFICIRY, null)?.toList()

}