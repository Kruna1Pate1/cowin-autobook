package com.krunal.cowinautobook.view

import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.caverock.androidsvg.SVG
import com.krunal.cowinautobook.R
import com.krunal.cowinautobook.databinding.FragmentRecaptchaBinding
import com.krunal.cowinautobook.utility.Status
import com.krunal.cowinautobook.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel

@AndroidEntryPoint
class RecaptchaFragment : Fragment(R.layout.fragment_recaptcha) {

    private val TAG = RecaptchaFragment::class.java.simpleName
    private lateinit var binding: FragmentRecaptchaBinding
    private lateinit var svg: SVG
    private lateinit var drawable: Drawable

    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: created")

        binding = FragmentRecaptchaBinding.bind(view)

        getRecaptcha()

        binding.btnRefresh.setOnClickListener {
            getRecaptcha()
        }

        binding.btnBook.setOnClickListener {
            bookSlot()
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
                    Log.d(TAG, "bookSlot: loadding")
                }
            }
        })
    }

    private fun getRecaptcha() {
        dashboardViewModel.getRecaptcha()
        dashboardViewModel.resRecaptch.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d(TAG, "getRecaptcha: success")
                    Log.d(TAG, "getRecaptcha: ${it.data?.captcha}")
                    svg = SVG.getFromString(it.data!!.captcha)
                    drawable = PictureDrawable(svg.renderToPicture())
                    binding.ivRecaptcha.setImageDrawable(drawable)
                }
                Status.UNAUTHORIZED -> {
                    Log.d(TAG, "getRecaptcha: unauthorized")
                }
                Status.ERROR -> {
                    Log.d(TAG, "getRecaptcha: error")
                }
                Status.LOADING -> {
                    Log.d(TAG, "getRecaptcha: loadding")
                }
            }
        })
    }

    private fun login() {
        lifecycleScope.cancel()
        activity?.supportFragmentManager?.popBackStack(
            "LoginFragment",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }
}