package com.htf.zbCard.ui.auth.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.zbCard.R
import com.htf.zbCard.base.BaseActivity
import com.htf.zbCard.databinding.ActivityLoginBinding
import com.htf.zbCard.databinding.ActivitySignupBinding
import com.htf.zbCard.databinding.HomeActivityBinding
import com.htf.zbCard.ui.auth.otpVerification.OtpVerificationActivity
import com.htf.zbCard.ui.auth.signUp.SignUpActivity
import com.htf.zbCard.ui.auth.signUp.SignUpViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.btnSignup

class LoginActivity: BaseActivity<ActivityLoginBinding, LoginViewModel>(
    LoginViewModel::class.java), View.OnClickListener  {

    private var currActivity: Activity =this
    override val layout: Int get() = R.layout.activity_login

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity, LoginActivity::class.java)
            currActivity.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.loginViewModel = viewModel
        setListener()
    }


    private fun setListener() {
        btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnLogin->{

            }
        }
    }

}