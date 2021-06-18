package com.krunal.cowinautobook.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.krunal.cowinautobook.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var homeFragment: HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        requestPermissions()

        homeFragment = HomeFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, homeFragment)
            commit()
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true);
    }

    private fun requestPermissions() {
        val smsPermission = Manifest.permission.RECEIVE_SMS;
        val grant = ContextCompat.checkSelfPermission(this, smsPermission);

        if (grant != PackageManager.PERMISSION_GRANTED) {
            val permissionList = arrayOfNulls<String>(1)
            permissionList[0] = smsPermission

            ActivityCompat.requestPermissions(this, permissionList, 100)
        }
    }
}