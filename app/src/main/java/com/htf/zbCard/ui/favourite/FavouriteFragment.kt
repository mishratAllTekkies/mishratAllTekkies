package com.htf.zbCard.ui.favourite
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.htf.zbCard.R
import com.htf.zbCard.base.BaseFragment
import com.htf.zbCard.databinding.FavouriteFragmentBinding

class FavouriteFragment : BaseFragment<FavouriteViewModel>(FavouriteViewModel::class.java) {
    private lateinit var binding: FavouriteFragmentBinding
    private lateinit var currActivity: Activity


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        currActivity = requireActivity()
        binding = DataBindingUtil.inflate(inflater, R.layout.favourite_fragment, container, false)
        binding.favViewModel = viewModel

        return binding.root
    }

}