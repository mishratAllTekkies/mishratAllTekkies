package com.htf.zbCard.ui.auth.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.zbCard.R
import com.htf.zbCard.base.BaseActivity
import com.htf.zbCard.databinding.ActivityUserNameBinding
import com.htf.zbCard.ui.auth.signUp.SignUpViewModel
import kotlinx.android.synthetic.main.activity_user_name.*
import kotlinx.android.synthetic.main.toolbar_primary.*

class UserNameActivity: BaseActivity<ActivityUserNameBinding, SignUpViewModel>(
 SignUpViewModel::class.java), View.OnClickListener  {

    private var currActivity: Activity =this
    override val layout: Int get() = R.layout.activity_user_name

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity, UserNameActivity::class.java)
            currActivity.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.userNameViewModel = viewModel
        setListener()
    }


    private fun setListener() {
        btn_continue_name.setOnClickListener(this)
        btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_continue_name->{
                  AboutUsActivity.open(currActivity)
            }
            R.id.btnBack->{
                onBackPressed()
            }
        }
    }

}