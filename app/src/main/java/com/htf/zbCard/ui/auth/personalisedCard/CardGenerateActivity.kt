package com.htf.zbCard.ui.auth.personalisedCard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.zbCard.R
import com.htf.zbCard.base.BaseActivity
import com.htf.zbCard.databinding.ActivityGenerateCardBinding
import com.htf.zbCard.ui.auth.signUp.SignUpViewModel

class CardGenerateActivity : BaseActivity<ActivityGenerateCardBinding, SignUpViewModel>(
    SignUpViewModel::class.java), View.OnClickListener {

    private var currActivity: Activity = this
    override val layout: Int get() = R.layout.activity_generate_card

    companion object {
        fun open(currActivity: Activity) {
            val intent = Intent(currActivity, CardGenerateActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.generateCardViewModel = viewModel
        //setListener()

    }
    private fun setListener() {

    }

    override fun onClick(v: View?) {
        when (v!!.id) {

        }
    }

}