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
import com.htf.zbCard.databinding.ActivityDateOfBirthBinding
import com.htf.zbCard.ui.auth.signUp.SignUpViewModel
import kotlinx.android.synthetic.main.activity_date_of_birth.*
import kotlinx.android.synthetic.main.dialog_id_information.view.*

class DateOfBirthActivity : BaseActivity<ActivityDateOfBirthBinding, SignUpViewModel>(
    SignUpViewModel::class.java), View.OnClickListener  {

    private var currActivity: Activity =this
    override val layout: Int get() = R.layout.activity_date_of_birth

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity, DateOfBirthActivity::class.java)
            currActivity.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.dobViewModel = viewModel
        setListener()
    }


    private fun setListener() {
        btn_born.setOnClickListener(this)
        ivInformation.setOnClickListener(this)
        rlt_date_select.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_born->{
                UserNameActivity.open(currActivity)
            }

            R.id.ivInformation->{
                userIdInformation(currActivity)
            }

            R.id.rlt_date_select->{
                userSelectDob(currActivity)
            }

        }
    }


    private fun userIdInformation(currActivity: Activity) {
        var builder: Dialog? = null

        builder = Dialog(currActivity)
        val dialogLangView = currActivity.layoutInflater.inflate(R.layout.dialog_id_information, null)
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


    private fun userSelectDob(currActivity: Activity) {
        var builder: Dialog? = null

        builder = Dialog(currActivity)
        val dialogLangView = currActivity.layoutInflater.inflate(R.layout.dialog_user_dob, null)
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