package com.krunal.cowinautobook.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.krunal.cowinautobook.R
import com.krunal.cowinautobook.adapter.BeneficiaryCbListener
import com.krunal.cowinautobook.adapter.BenificiaryAdapter
import com.krunal.cowinautobook.databinding.FragmentDashboardBinding
import com.krunal.cowinautobook.models.Beneficiary
import com.krunal.cowinautobook.models.BeneficiaryResponse
import com.krunal.cowinautobook.pref.SharedPrefs
import com.krunal.cowinautobook.utility.Status
import com.krunal.cowinautobook.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.properties.Delegates


@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private val TAG = DashboardFragment::class.java.simpleName
    private lateinit var binding: FragmentDashboardBinding
    private var slotStatus: Boolean = false
    private val dashboardViewModel: DashboardViewModel by viewModels()
    private lateinit var loginFragment: LoginFragment
    private lateinit var beneficiaryAdapter: BenificiaryAdapter
    lateinit var arrayList: MutableList<Beneficiary>
    private var dose by Delegates.notNull<Int>()
    private var delayTime: Long = 5000L


    @Inject
    lateinit var sharedPrefs: SharedPrefs

    @Inject
    lateinit var tomorrowsDate: String

    private lateinit var beneficiaryListener: BeneficiaryCbListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)
        binding.tvPinCode.text = sharedPrefs.getPinCode()
        binding.tvDate.text = tomorrowsDate


        // Start looking for slot with coroutine

        lifecycleScope.launch {
            slotCheck()
        }

        arrayList = arrayListOf()

        // Listener for ListView beneficiary checkbox
        beneficiaryListener = object : BeneficiaryCbListener {
            var list: MutableList<String> = mutableListOf()
            override fun onChecked(beneficiaryId: String) {
                Log.d(TAG, "onChecked: $beneficiaryId")
                if (!sharedPrefs.getBenificiary().isNullOrEmpty()) {
                    list = sharedPrefs.getBenificiary()!!.toMutableList()
                }
                list.add(beneficiaryId)
                sharedPrefs.setBenificiary(list.toList())
            }

            override fun onUnchecked(beneficiaryId: String) {
                Log.d(TAG, "onUnchecked: $beneficiaryId")
                if (!sharedPrefs.getBenificiary().isNullOrEmpty()) {
                    list = sharedPrefs.getBenificiary()!!.toMutableList()
                    list.remove(beneficiaryId)
                    sharedPrefs.setBenificiary(list.toList())
                }
            }

            override fun isExist(beneficiaryId: String): Boolean {
                Log.d(TAG, "isExist: $beneficiaryId")
                if (!sharedPrefs.getBenificiary()
                        .isNullOrEmpty()
                ) return sharedPrefs.getBenificiary()!!.contains(beneficiaryId)
                return false
            }
        }
        beneficiaryAdapter = BenificiaryAdapter(
            requireActivity(),
            R.layout.beneficiary,
            arrayList as ArrayList<Beneficiary>,
            beneficiaryListener
        )
        binding.lvBeneficiary.adapter = beneficiaryAdapter

        getBeneficiary()

        binding.btnChange.setOnClickListener {
            home()
        }

    }

    private suspend fun slotCheck() {
        dose = sharedPrefs.getDose()
        while (!slotStatus) {
            Log.d(TAG, "slotCheck: dose $dose")
            dashboardViewModel.getSloatDetails()
            dashboardViewModel.resSlotData.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.sessions?.forEach { session ->
                            if (session.available_capacity > 0) {
                                if ((dose == 1 && session.available_capacity_dose1 > 1) || (dose == 2 && session.available_capacity_dose2 > 1)) {
                                    Log.d(TAG, "slotCheck: ${session.session_id}")
                                    slotStatus = true
                                    dashboardViewModel.sessionId = session.session_id
                                    dashboardViewModel.centerId = session.center_id
                                    bookSlot()
                                    lifecycleScope.cancel()
                                }
                            }
                        }
                    }
                    Status.UNAUTHORIZED -> {
                        Log.d(TAG, "slotCheck: unauthorized")
                    }
                    Status.ERROR -> {
                        Log.d(TAG, "slotCheck: error")
                    }
                    Status.LOADING -> {
                        Log.d(TAG, "slotCheck: loading")
                    }
                }
            })
            delay(delayTime)
        }
    }

    private fun bookSlot() {
        dashboardViewModel.schedule()
        dashboardViewModel.resSchedule.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d(TAG, "bookSlot: success")
                    it.data?.appointment_id
                }
                Status.UNAUTHORIZED -> {
                    Log.d(TAG, "bookSlot: unauthorized")
                    login()
                }
                Status.ERROR -> {
                    Log.d(TAG, "bookSlot: error")
                    login()
                }
                Status.LOADING -> {
                    Log.d(TAG, "bookSlot: loading")
                }
            }
        })
    }


    private fun getBeneficiary() {
        dashboardViewModel.getBeneficiary()
        dashboardViewModel.resBeneficiary.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d(TAG, "getBeneficiary: success")
                    loadBeneficiary(it.data!!)
                }
                Status.UNAUTHORIZED -> {
                    Log.d(TAG, "getBeneficiary: unauthorized")
                    login()
                }
                Status.ERROR -> {
                    Log.d(TAG, "getBeneficiary: error")
                    login()
                }
                Status.LOADING -> {
                    Log.d(TAG, "getBeneficiary: loading")
                }
            }
        })
    }


    private fun loadBeneficiary(beneficiary: BeneficiaryResponse) {
        beneficiary.beneficiaries.forEach {
            arrayList.add(Beneficiary(it.name, it.beneficiary_reference_id, it.photo_id_number))
            beneficiaryAdapter.notifyDataSetChanged()
        }
    }


    private fun home() {
        lifecycleScope.cancel()
        activity?.supportFragmentManager?.popBackStack(
            "HomeFragment",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }


    private fun login() {
        lifecycleScope.cancel()
        activity?.supportFragmentManager?.popBackStack(
            "LoginFragment",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }


    // Recaptcha is no more required

    private fun recaptcha() {
        lifecycleScope.cancel()
        val recaptchaFragment = RecaptchaFragment()
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragment_container, recaptchaFragment)
            addToBackStack("DashboardFragment")
            commit()
        }
    }
}