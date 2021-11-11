package com.htf.zbCard.ui.cart

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.htf.zbCard.R
import com.htf.zbCard.base.BaseFragment
import com.htf.zbCard.databinding.CartFragmentBinding

class CartFragment  : BaseFragment<CartViewModel>(CartViewModel::class.java) {
    private lateinit var binding: CartFragmentBinding
    private lateinit var currActivity: Activity


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        currActivity = requireActivity()
        binding = DataBindingUtil.inflate(inflater, R.layout.cart_fragment, container, false)
        binding.cartViewModel = viewModel

        return binding.root
    }
}
