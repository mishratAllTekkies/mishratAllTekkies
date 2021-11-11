package com.htf.zbCard.ui.splash

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import com.htf.zbCard.R
import com.htf.zbCard.base.BaseActivity
import com.htf.zbCard.base.MyApplication
import com.htf.zbCard.databinding.SplashActivityBinding
import com.htf.zbCard.netUtils.Constants
import com.htf.zbCard.ui.auth.signUp.SignUpActivity
import com.htf.zbCard.utils.AppPreferences
import com.htf.zbCard.utils.AppSession

class SplashActivity : BaseActivity<SplashActivityBinding, SplashViewModel>(SplashViewModel::class.java) {
    private var currActivity: Activity =this

    override val layout: Int get() = R.layout.splash_activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding.splashViewModel=viewModel
        AppPreferences.getInstance(currActivity).saveInPreference(Constants.KEY_PREF_USER_LANGUAGE,AppSession.locale)
        val currUser= AppPreferences.getInstance(MyApplication.getAppContext()).getUserDetails()
        if (currUser!=null){
            Handler(Looper.getMainLooper()).postDelayed({
                SignUpActivity.open(currActivity)
                finish()
            },3000)
        } else{
            Handler(Looper.getMainLooper()).postDelayed({
                SignUpActivity.open(currActivity)
                finish()
            },3000)
         /*     Handler(Looper.getMainLooper()).postDelayed({
                DetectLocationActivity.open(currActivity)
                finish()
         },3000)*/

        }

    }
}