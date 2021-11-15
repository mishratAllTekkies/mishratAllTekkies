package com.htf.zbCard.ui.auth.personalisedCard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.htf.zbCard.R
import com.htf.zbCard.base.BaseActivity
import com.htf.zbCard.databinding.ActivityPersonalisedCardBinding
import com.htf.zbCard.ui.auth.signUp.SignUpActivity
import com.htf.zbCard.ui.auth.signUp.SignUpViewModel
import kotlinx.android.synthetic.main.activity_complete_kyc.*

class PersonalisedCardActivity : BaseActivity<ActivityPersonalisedCardBinding, SignUpViewModel>(
    SignUpViewModel::class.java), View.OnClickListener {

    private var currActivity: Activity = this
    override val layout: Int get() = R.layout.activity_personalised_card

    companion object {
        fun open(currActivity: Activity) {
            val intent = Intent(currActivity, PersonalisedCardActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.personalCardViewModel = viewModel
        setListener()
        Handler(Looper.getMainLooper()).postDelayed({
            CardGenerateActivity.open(currActivity)
            finish()
        },2000)

    }


    private fun setListener() {
       // btn_start_kyc.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
          /*  R.id.btn_start_kyc -> {
                //    userCompleteKyc(currActivity)
            }*/
        }
    }

}