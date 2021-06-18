package com.krunal.cowinautobook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krunal.cowinautobook.models.*
import com.krunal.cowinautobook.pref.SharedPrefs
import com.krunal.cowinautobook.repository.MainRepository
import com.krunal.cowinautobook.utility.Constants
import com.krunal.cowinautobook.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val sharedPrefs: SharedPrefs,
    private val tommorowsDate: String
) : ViewModel() {


    private var authToken = "Bearer " + sharedPrefs.getToken()
    private var dose = sharedPrefs.getDose()
    private var slotTime = Constants.SLOT_TIME
    private var beneficiaries: MutableList<String> = arrayListOf<String>()
    lateinit var sessionId: String
    var centerId by Delegates.notNull<Int>()

    // Get benificiaries details

    private val _resBeneficiary = MutableLiveData<Resource<BeneficiaryResponse>>()
    val resBeneficiary: LiveData<Resource<BeneficiaryResponse>>
        get() = _resBeneficiary

    fun getBeneficiary() =
        viewModelScope.launch {
            _resBeneficiary.postValue(Resource.loading(null))
            mainRepository.getBeneficiary(authToken)
                .let {
                    if (it.isSuccessful) {
                        _resBeneficiary.postValue(Resource.success(it.body()))
                    } else {
                        _resBeneficiary.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
        }


    private val _resSlotData = MutableLiveData<Resource<SloatDataResponse>>()
    val resSlotData: LiveData<Resource<SloatDataResponse>>
        get() = _resSlotData

    fun getSloatDetails() = viewModelScope.launch {
        _resSlotData.postValue(Resource.loading(null))
        mainRepository.getSloatDetails(sharedPrefs.getPinCode(), tommorowsDate).let {
            if (it.isSuccessful) {
                _resSlotData.postValue(Resource.success(it.body()))
            } else {
                _resSlotData.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }

    // Book slot

    private val _resSchedule = MutableLiveData<Resource<ScheduleResponse>>()
    val resSchedule: LiveData<Resource<ScheduleResponse>>
        get() = _resSchedule

    fun schedule() = viewModelScope.launch {
        _resSchedule.postValue(Resource.loading(null))
        mainRepository.schedule(
            Schedule(
                dose = dose,
                sessionId = sessionId,
                centerId = centerId,
                slot = slotTime,
                beneficiaries = beneficiaries
            ), authToken
        ).let {
            if (it.isSuccessful) {
                _resSchedule.postValue(Resource.success(it.body()))
            } else {
                _resSchedule.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }


    // Captcha is no more needed

    private val _resRecaptch = MutableLiveData<Resource<RecaptchaResponse>>()
    val resRecaptch: LiveData<Resource<RecaptchaResponse>>
        get() = _resRecaptch

    fun getRecaptcha() =
        viewModelScope.launch {
            _resRecaptch.postValue(Resource.loading(null))
            mainRepository.getRecaptcha(authToken)
                .let {
                    if (it.isSuccessful) {
                        _resRecaptch.postValue(Resource.success(it.body()))
                    } else {
                        _resRecaptch.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
        }

}