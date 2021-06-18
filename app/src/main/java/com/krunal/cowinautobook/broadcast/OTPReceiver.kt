package com.krunal.cowinautobook.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import com.krunal.cowinautobook.utility.Constants
import javax.inject.Inject


class OTPReceiver @Inject constructor() : BroadcastReceiver() {

    companion object {
        lateinit var smsListener: SmsListener
    }

    fun setSmsListner(smsListener: SmsListener) {
        OTPReceiver.smsListener = smsListener
    }

    private lateinit var otp: String

    override fun onReceive(context: Context?, intent: Intent?) {

        val messages: Array<SmsMessage> = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        Log.d("TAG", "onReceive: ")
        for (sms in messages) {
            val message: String = sms.messageBody

            if (message.contains("cowin", ignoreCase = true)) {
                otp = Constants.getOtpFromMsg(message)
                Log.d("BroadCast", "onReceive: $otp")

                smsListener.onOtpReceive(otp)
            }

        }
    }
}