package com.htf.zbCard.ui.category

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.htf.zbCard.R
import com.htf.zbCard.base.BaseFragment
import com.htf.zbCard.databinding.FragmentSubCategoryBinding
import com.htf.zbCard.ui.home.DashboardModel
import com.htf.zbCard.ui.homeDetail.CategoryDetailViewModel

class SubCategoryFragment :BaseFragment<CategoryDetailViewModel>(CategoryDetailViewModel::class.java)  {

    private lateinit var currActivity: Activity
    private lateinit var binding: FragmentSubCategoryBinding

    companion object {
        fun create(subCategory: DashboardModel, parentId: String): SubCategoryFragment {
            val fragment = SubCategoryFragment()
            val bundle = Bundle()
            bundle.putSerializable("subCategory", subCategory)
            bundle.putString("id", parentId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        currActivity = requireActivity()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sub_category, container, false)
        //navController = findNavController()
        //setListener()
       // binding.dashBoardViewModel = viewModel

        return binding.root
    }


}