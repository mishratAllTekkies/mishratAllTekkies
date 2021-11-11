package com.htf.zbCard.ui.order

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.htf.zbCard.R
import com.htf.zbCard.base.BaseFragment
import com.htf.zbCard.databinding.OrderFragmentBinding

class OrderFragment : BaseFragment<OrderViewModel>(OrderViewModel::class.java) {
    private lateinit var binding: OrderFragmentBinding
    private lateinit var currActivity: Activity


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        currActivity = requireActivity()
        binding = DataBindingUtil.inflate(inflater, R.layout.order_fragment, container, false)
        binding.orderViewModel = viewModel

        return binding.root
    }
}