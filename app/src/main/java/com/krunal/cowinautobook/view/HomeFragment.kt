package com.krunal.cowinautobook.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.krunal.cowinautobook.R
import com.krunal.cowinautobook.databinding.FragmentHomeBinding
import com.krunal.cowinautobook.pref.SharedPrefs
import com.krunal.cowinautobook.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.krunal.cowinautobook.utility.Status

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {


    private val TAG = MainActivity::class.java.simpleName

    private lateinit var binding: FragmentHomeBinding

    private lateinit var pinCode: String
    private lateinit var phone: String
    lateinit var loginFragment: LoginFragment
    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var sharedPrefs: SharedPrefs


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentHomeBinding.bind(view)

        loginFragment = LoginFragment()

        binding.btnSave.setOnClickListener {
            saveData()

            if (sharedPrefs.getLoginStatus()) loginStatus()
            else login()

        }

        binding.rgDose.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == binding.rbDose1.id) {
                sharedPrefs.setDose(1)
                binding.rbDose1.isChecked = true
                binding.rbDose2.isChecked = false
            } else {
                sharedPrefs.setDose(2)
                binding.rbDose1.isChecked = false
                binding.rbDose2.isChecked = true
            }
        }

        loadData()
    }

    private fun loginStatus() {
        mainViewModel.getCalendar()
        mainViewModel.resCal.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d(TAG, "loginStatus: success")
                    sharedPrefs.setLoginStatus(true)
                    login()
                }
                Status.UNAUTHORIZED -> {
                    Log.d(TAG, "loginStatus: unauthorized")
                    sharedPrefs.setLoginStatus(false)
                    login()
                }
                Status.ERROR -> {
                    Log.d(TAG, "loginStatus: error")
                    sharedPrefs.setLoginStatus(false)
                    login()
                }
                Status.LOADING -> {
                    Log.d(TAG, "loginStatus: loading")
                }
            }
        })

    }

    private fun login() {
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragment_container, loginFragment)
            addToBackStack("HomeFragment")
            commit()
        }
    }

    private fun saveData() {
        sharedPrefs.setPinCode(binding.etPinCode.text.toString())
        sharedPrefs.setPhoneNum(binding.etPhone.text.toString())


    }

    private fun loadData() {
        pinCode = sharedPrefs.getPinCode()
        phone = sharedPrefs.getPhoneNum()
        binding.etPinCode.setText(pinCode)
        binding.etPhone.setText(phone)

        if (sharedPrefs.getDose() == 1) {
            binding.rbDose1.isChecked = true
        } else {
            binding.rbDose2.isChecked = true
        }
    }

}