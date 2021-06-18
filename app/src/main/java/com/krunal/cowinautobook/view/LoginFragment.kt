package com.krunal.cowinautobook.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.krunal.cowinautobook.R
import com.krunal.cowinautobook.broadcast.OTPReceiver
import com.krunal.cowinautobook.broadcast.SmsListener
import com.krunal.cowinautobook.databinding.FragmentLoginBinding
import com.krunal.cowinautobook.pref.SharedPrefs
import com.krunal.cowinautobook.utility.Constants
import com.krunal.cowinautobook.utility.Status
import com.krunal.cowinautobook.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val TAG = LoginFragment::class.java.simpleName

    lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    @Inject
    lateinit var otpReceiver: OTPReceiver

    private lateinit var txnId: String
    private lateinit var otp: String
    private lateinit var authToken: String

    private lateinit var dashboardFragment: DashboardFragment

    private val loginViewModel: LoginViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: Created")

        dashboardFragment = DashboardFragment()

        binding = FragmentLoginBinding.bind(view)

        binding.tvPhone.text = sharedPrefs.getPhoneNum()
        binding.tvPinCode.text = sharedPrefs.getPinCode()

        otpReceiver.setSmsListner(object : SmsListener {
            override fun onOtpReceive(otp: String) {
                Log.d(TAG, "onOtpReceive: from main")
                binding.etOtp.setText(otp)
                this@LoginFragment.otp = otp
                validateOtp()
            }
        })

        if (sharedPrefs.getLoginStatus()) {
            dashboard()
        } else {
            generateOtp()
        }


        binding.btnLogin.setOnClickListener {
            otp = binding.etOtp.text.toString()
            otp = Constants.sha256Convertor(otp)
            validateOtp()
        }

    }

    private fun generateOtp() {
        loginViewModel.resGenerateOtp.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d(TAG, "generateOtp: success")
                    txnId = it.data!!.txnId
                }
                Status.UNAUTHORIZED -> {
                    Log.d(TAG, "generateOtp: unauthorized")
                }
                Status.ERROR -> {
                    Log.d(TAG, "generateOtp: error")
                }
                Status.LOADING -> {
                    Log.d(TAG, "generateOtp: loadding")
                }
            }
        })

    }

    private fun validateOtp() {
        loginViewModel.validateOtp(txnId, otp)
        loginViewModel.resValidateOTP.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d(TAG, "confirmOtp: success")
                    authToken = it.data!!.token
                    sharedPrefs.setToken(authToken)
                    sharedPrefs.setLoginStatus(true)
                    dashboard()
                }
                Status.UNAUTHORIZED -> {
                    Log.d(TAG, "confirmOtp: unauthorized")
                }
                Status.ERROR -> {
                    Log.d(TAG, "confirmOtp: error")
                }
                Status.LOADING -> {
                    Log.d(TAG, "confirmOtp: loadding")
                }
            }
        })
    }


    private fun dashboard() {
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragment_container, dashboardFragment)
            addToBackStack("LoginFragment")
            commit()
        }
    }

}