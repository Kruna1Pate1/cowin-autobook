package com.krunal.cowinautobook.broadcast

interface SmsListener {
    fun onOtpReceive(otp: String)
}