package com.htf.zbCard.ui.auth.otpVerification

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.zbCard.R
import com.htf.zbCard.base.BaseActivity
import com.htf.zbCard.databinding.ActivityOtpVerifyBinding
import com.htf.zbCard.ui.auth.profile.DateOfBirthActivity
import com.htf.zbCard.ui.auth.signUp.SignUpViewModel
import com.htf.zbCard.ui.location.MapActivity
import kotlinx.android.synthetic.main.activity_otp_verify.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.toolbar_primary.*

class OtpVerificationActivity: BaseActivity<ActivityOtpVerifyBinding, SignUpViewModel>(
    SignUpViewModel::class.java), View.OnClickListener  {

    private var currActivity: Activity =this
    override val layout: Int get() = R.layout.activity_otp_verify

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity, OtpVerificationActivity::class.java)
            currActivity.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.otpViewModel = viewModel
        setListener()
    }


    private fun setListener() {
        btn_otp_verify.setOnClickListener(this)
        btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_otp_verify->{
                DateOfBirthActivity.open(currActivity)
            }
            R.id.btnBack->{
                onBackPressed()
            }
        }
    }

}