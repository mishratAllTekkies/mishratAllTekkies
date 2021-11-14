package com.htf.zbCard.ui.auth.signUp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.zbCard.R
import com.htf.zbCard.base.BaseActivity
import com.htf.zbCard.databinding.ActivitySignupBinding
import com.htf.zbCard.ui.auth.login.LoginActivity
import com.htf.zbCard.ui.auth.otpVerification.OtpVerificationActivity
import com.htf.zbCard.ui.location.MapActivity
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.detect_location_activity.*
import kotlinx.android.synthetic.main.detect_location_activity.btn_detect_location

class SignUpActivity : BaseActivity<ActivitySignupBinding, SignUpViewModel>(
    SignUpViewModel::class.java),View.OnClickListener  {

    private var currActivity: Activity =this
    override val layout: Int get() = R.layout.activity_signup

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity, SignUpActivity::class.java)
            currActivity.startActivity(intent)
        }
     }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.signUpViewModel = viewModel
        setListener()
     }


    private fun setListener() {
        btnSignup.setOnClickListener(this)
        ll_login.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSignup-> {
                OtpVerificationActivity.open(currActivity)
            }

            R.id.ll_login->{
                    LoginActivity.open(currActivity)
                }
            }
    }

}