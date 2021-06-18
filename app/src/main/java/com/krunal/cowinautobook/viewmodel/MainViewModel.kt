package com.krunal.cowinautobook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krunal.cowinautobook.models.CalendarResponse
import com.krunal.cowinautobook.models.SloatDataResponse
import com.krunal.cowinautobook.pref.SharedPrefs
import com.krunal.cowinautobook.repository.MainRepository
import com.krunal.cowinautobook.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val sharedPrefs: SharedPrefs,
    private val tomorrowsDate: String
) : ViewModel() {

    private val _resSlotData = MutableLiveData<Resource<SloatDataResponse>>()

    val resSlotData: LiveData<Resource<SloatDataResponse>>
        get() = _resSlotData

    private val _resCal = MutableLiveData<Resource<CalendarResponse>>()

    val resCal: LiveData<Resource<CalendarResponse>>
        get() = _resCal

    val authToken: String = "Bearer " + sharedPrefs.getToken()

    private fun getSloatDetails() = viewModelScope.launch {
        _resSlotData.postValue(Resource.loading(null))
        mainRepository.getSloatDetails(sharedPrefs.getPinCode(), tomorrowsDate).let {
            if (it.isSuccessful) {
                _resSlotData.postValue(Resource.success(it.body()))
            } else {
                _resSlotData.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }

    fun getCalendar() = viewModelScope.launch {
        _resCal.postValue(Resource.loading(null))
        mainRepository.getCalendar(sharedPrefs.getPinCode(), tomorrowsDate, authToken).let {
            if (it.isSuccessful) {
                _resCal.postValue(Resource.success(it.body()))
            } else {
                _resCal.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }
}