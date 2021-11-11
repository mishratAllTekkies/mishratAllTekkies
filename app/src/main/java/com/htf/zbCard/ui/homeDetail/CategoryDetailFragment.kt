package com.htf.zbCard.ui.homeDetail

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.htf.zbCard.R
import com.htf.zbCard.adapter.ViewPagerAdapter
import com.htf.zbCard.base.BaseFragment
import com.htf.zbCard.databinding.CategoryDetailFragmentBinding
import com.htf.zbCard.ui.category.SubCategoryFragment
import com.htf.zbCard.ui.home.DashBoardAdapter.Companion.CATEGORY_MODEL
import com.htf.zbCard.ui.home.DashboardModel
import com.htf.zbCard.utils.observerViewModel
import com.htf.zbCard.utils.showToast
import kotlinx.android.synthetic.main.category_detail_fragment.*
import kotlinx.android.synthetic.main.category_detail_fragment.view.*
import kotlinx.android.synthetic.main.dashborad_fragment.view.*

class CategoryDetailFragment  : BaseFragment<CategoryDetailViewModel>(CategoryDetailViewModel::class.java) {
    private lateinit var binding: CategoryDetailFragmentBinding
    private lateinit var currActivity: Activity
    private lateinit var adapter1: ViewPagerAdapter
    private var arrSubCategoryTab = ArrayList<DashboardModel>()
    private var categoryID = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        currActivity = requireActivity()
        binding = DataBindingUtil.inflate(inflater, R.layout.category_detail_fragment, container, false)
        binding.detailsViewModel = viewModel
        val categoryModel = arguments?.getSerializable(CATEGORY_MODEL) as DashboardModel

        /* Set Toolbar Text*/
        binding.appBarLayoutMenuInclude.toolbarTitle.text =categoryModel.name
        categoryID=categoryModel.id.toString()
        val navController = findNavController()
        binding.appBarLayoutMenuInclude.toolbarMenu.setupWithNavController(navController)

        viewModel.categoryList(categoryModel.id.toString())
        viewModelInitialize()
        setupViewPager1()
        return binding.root
    }

    private fun setupViewPager1() {
        adapter1 = ViewPagerAdapter(childFragmentManager)
        for(subCategory in arrSubCategoryTab) {
            adapter1.addFragment(SubCategoryFragment.create(subCategory,categoryID),subCategory.name!!)
        }
        binding.root.viewpager.adapter = adapter1
        binding.root.tabs.setupWithViewPager(viewpager)
        binding.root.viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

             }

            override fun onPageScrolled(position: Int, positionOffset: Float,positionOffsetPixels: Int) {

              }

            override fun onPageSelected(position: Int) {

            }

          })
      }

    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling, this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult, this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mCategoryListData, this::onHandleDashBoardSuccessResponse)
    }

    private fun onHandleShowProgress(isNotShow: Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String) {
        currActivity.showToast(error, true)
    }


        private fun onHandleDashBoardSuccessResponse(dashBoardModel: ArrayList<DashboardModel>?) {
        dashBoardModel?.let {
            if (dashBoardModel.isNotEmpty()) {
                arrSubCategoryTab.clear()
                val category = DashboardModel()
                category.id = ""
                category.name = getString(R.string.all)
                arrSubCategoryTab.add(category)
                arrSubCategoryTab.addAll(dashBoardModel)
                setupViewPager1()
               // findNavController().popBackStack(R.id.home_dest, false)

            } else {

            }
        }
    }




}