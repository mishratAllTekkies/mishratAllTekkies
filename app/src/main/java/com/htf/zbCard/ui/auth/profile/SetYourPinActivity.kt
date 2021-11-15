package com.htf.zbCard.ui.auth.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.zbCard.R
import com.htf.zbCard.base.BaseActivity
import com.htf.zbCard.databinding.ActivitySetYourPinBinding
import com.htf.zbCard.ui.auth.completeKyc.CompleteKycActivity
import com.htf.zbCard.ui.auth.signUp.SignUpViewModel
import kotlinx.android.synthetic.main.activity_set_your_pin.*
import kotlinx.android.synthetic.main.toolbar_primary.*

class SetYourPinActivity :BaseActivity<ActivitySetYourPinBinding, SignUpViewModel>(
    SignUpViewModel::class.java), View.OnClickListener {

    private var currActivity: Activity = this
    override val layout: Int get() = R.layout.activity_set_your_pin

    companion object {
        fun open(currActivity: Activity) {
            val intent = Intent(currActivity, SetYourPinActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setPinViewModel = viewModel
        setListener()
    }


    private fun setListener() {
        btn_set_login_pin.setOnClickListener(this)
        btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_set_login_pin -> {
                CompleteKycActivity.open(currActivity)
            }

            R.id.btnBack->{
                onBackPressed()
            }
        }
    }

}