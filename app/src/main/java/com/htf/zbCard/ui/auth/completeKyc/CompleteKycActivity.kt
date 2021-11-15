package com.htf.zbCard.ui.auth.completeKyc

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.htf.zbCard.R
import com.htf.zbCard.base.BaseActivity
import com.htf.zbCard.databinding.ActivityCompleteKycBinding
import com.htf.zbCard.ui.auth.personalisedCard.PersonalisedCardActivity
import com.htf.zbCard.ui.auth.signUp.SignUpViewModel
import kotlinx.android.synthetic.main.activity_complete_kyc.*
import kotlinx.android.synthetic.main.activity_set_your_pin.*
import kotlinx.android.synthetic.main.dialog_start_kyc.view.*
import kotlinx.android.synthetic.main.dialog_start_kyc.view.ivCancel
import kotlinx.android.synthetic.main.dialog_verify_kyc.view.*
import kotlinx.android.synthetic.main.toolbar_primary.*

open class CompleteKycActivity :BaseActivity<ActivityCompleteKycBinding, SignUpViewModel>(
    SignUpViewModel::class.java), View.OnClickListener {

    private var currActivity: Activity = this
    override val layout: Int get() = R.layout.activity_complete_kyc

    companion object {
        fun open(currActivity: Activity) {
            val intent = Intent(currActivity, CompleteKycActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.completeKycViewModel = viewModel
        setListener()
    }


    private fun setListener() {
        btn_start_kyc.setOnClickListener(this)
        btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_start_kyc -> {
                userCompleteKyc(currActivity)
            }
            R.id.btnBack->{
                onBackPressed()
            }

        }
    }


    private fun userCompleteKyc(currActivity: Activity) {
        var builder: Dialog? = null

        builder = Dialog(currActivity)
        val dialogLangView =
            currActivity.layoutInflater.inflate(R.layout.dialog_start_kyc, null)
        builder.setContentView(dialogLangView)
        builder.setCancelable(true)

        dialogLangView.btn_scan_id.setOnClickListener {
            AppScanActivity.start(this)
        }
       dialogLangView.tv_browse_doc.setOnClickListener {
           kycVerificationCode(currActivity)
           builder!!.dismiss()
        }

        dialogLangView.ivCancel.setOnClickListener {
            builder!!.dismiss()
        }

        builder!!.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        builder!!.window?.setGravity(Gravity.BOTTOM)
        builder!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        builder!!.show()
    }



    private fun kycVerificationCode(currActivity: Activity) {
        var builder: Dialog? = null

        builder = Dialog(currActivity)
        val dialogLangView =
            currActivity.layoutInflater.inflate(R.layout.dialog_verify_kyc, null)
        builder.setContentView(dialogLangView)
        builder.setCancelable(true)


        dialogLangView.btn_verify_otp.setOnClickListener {
            kycSuccessfullyDoneDialog(currActivity)
            builder!!.dismiss()
        }

        dialogLangView.ivCancel.setOnClickListener {
            builder!!.dismiss()
        }

        builder!!.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        builder!!.window?.setGravity(Gravity.BOTTOM)
        builder!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        builder!!.show()
    }



    /*  Kyc Successfully done Dialog*/

    private fun kycSuccessfullyDoneDialog(currActivity: Activity) {
        var builder: Dialog? = null

        builder = Dialog(currActivity)
        val dialogLangView =
            currActivity.layoutInflater.inflate(R.layout.dialog_kyc_successfully_done, null)
        builder.setContentView(dialogLangView)
        builder.setCancelable(true)

        dialogLangView.btn_verify_otp.setOnClickListener {
            PersonalisedCardActivity.open(currActivity)
            builder!!.dismiss()
        }


        dialogLangView.ivCancel.setOnClickListener {
            builder!!.dismiss()
        }

        builder!!.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        builder!!.window?.setGravity(Gravity.BOTTOM)
        builder!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        builder!!.show()
    }



}