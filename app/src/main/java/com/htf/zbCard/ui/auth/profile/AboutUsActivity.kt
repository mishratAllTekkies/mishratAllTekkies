package com.htf.zbCard.ui.auth.profile

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
import com.htf.zbCard.databinding.ActivityAboutUsBinding
import com.htf.zbCard.ui.auth.signUp.SignUpViewModel
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.dialog_id_information.view.*

class AboutUsActivity : BaseActivity<ActivityAboutUsBinding, SignUpViewModel>(
    SignUpViewModel::class.java), View.OnClickListener {

    private var currActivity: Activity = this
    override val layout: Int get() = R.layout.activity_about_us

    companion object {
        fun open(currActivity: Activity) {
            val intent = Intent(currActivity, AboutUsActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.aboutUsViewModel = viewModel
        setListener()
    }


    private fun setListener() {
        btn_continue_name.setOnClickListener(this)
        rlt_nationality.setOnClickListener(this)
        rlt_gender.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_continue_name -> {
                SetYourPinActivity.open(currActivity)
             }

            R.id.rlt_nationality -> {
                selectCountry(currActivity)
             }

            R.id.rlt_gender -> {
                userGender(currActivity)
             }
        }
    }


    private fun selectCountry(currActivity: Activity) {
        var builder: Dialog? = null
        builder = Dialog(currActivity)
        val dialogLangView = currActivity.layoutInflater.inflate(R.layout.dialog_select_country, null)
        builder.setContentView(dialogLangView)
        builder.setCancelable(true)

        dialogLangView.ivCancel.setOnClickListener {
            builder.dismiss()
        }

        builder.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        builder.window?.setGravity(Gravity.BOTTOM)
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        builder.show()

    }

    private fun userGender(currActivity: Activity){

        var builder: Dialog? = null
        builder = Dialog(currActivity)
        val dialogLangView = currActivity.layoutInflater.inflate(R.layout.dialog_user_gender, null)
        builder.setContentView(dialogLangView)
        builder.setCancelable(true)

        dialogLangView.ivCancel.setOnClickListener {
            builder.dismiss()
        }

        builder.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        builder.window?.setGravity(Gravity.BOTTOM)
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        builder.show()
    }

}