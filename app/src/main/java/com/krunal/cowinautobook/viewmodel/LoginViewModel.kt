package com.krunal.cowinautobook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krunal.cowinautobook.models.GenerateOTP
import com.krunal.cowinautobook.models.GenerateOTPResponse
import com.krunal.cowinautobook.models.validateOTP
import com.krunal.cowinautobook.models.validateOTPResponse
import com.krunal.cowinautobook.pref.SharedPrefs
import com.krunal.cowinautobook.repository.MainRepository
import com.krunal.cowinautobook.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {

    private val _resGenerateOtp = MutableLiveData<Resource<GenerateOTPResponse>>()

    val resGenerateOtp: LiveData<Resource<GenerateOTPResponse>>
        get() = _resGenerateOtp

    private val _resValidateOtp = MutableLiveData<Resource<validateOTPResponse>>()

    val resValidateOTP: LiveData<Resource<validateOTPResponse>>
        get() = _resValidateOtp

    init {
        generateOtp(sharedPrefs.getPhoneNum())
    }

    private fun generateOtp(mobile: String) =
        viewModelScope.launch {
            _resGenerateOtp.postValue(Resource.loading(null))
            mainRepository.generateOtp(GenerateOTP(mobile))
                .let {
                    if (it.isSuccessful) {
                        _resGenerateOtp.postValue(Resource.success(it.body()))
                    } else {
                        _resGenerateOtp.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
        }

    fun validateOtp(txnId: String, otp: String) =
        viewModelScope.launch {
            _resValidateOtp.postValue(Resource.loading(null))
            mainRepository.validateOtp(validateOTP(txnId, otp))
                .let {
                    if (it.isSuccessful) {
                        _resValidateOtp.postValue(Resource.success(it.body()))
                    } else {
                        _resValidateOtp.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
        }
}