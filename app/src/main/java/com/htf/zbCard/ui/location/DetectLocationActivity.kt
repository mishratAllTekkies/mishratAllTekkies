package com.htf.zbCard.ui.location

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.htf.zbCard.R
import com.htf.zbCard.base.BaseActivity
import com.htf.zbCard.databinding.DetectLocationActivityBinding
import kotlinx.android.synthetic.main.detect_location_activity.*

class DetectLocationActivity : BaseActivity<DetectLocationActivityBinding, DetectLocationViewModel>(DetectLocationViewModel::class.java),
    View.OnClickListener {
    private var currActivity: Activity =this
    override val layout: Int get() = R.layout.detect_location_activity

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,DetectLocationActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.detectLocationViewModel = viewModel
        setListener()
    }

    private fun setListener() {
        btn_detect_location.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_detect_location->{
              MapActivity.open(currActivity)
            }
        }
    }

}