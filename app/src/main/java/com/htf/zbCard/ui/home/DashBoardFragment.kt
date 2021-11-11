package com.htf.zbCard.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.htf.zbCard.R
import com.htf.zbCard.base.BaseFragment
import com.htf.zbCard.databinding.DashboradFragmentBinding
import com.htf.zbCard.utils.observerViewModel
import com.htf.zbCard.utils.showToast
import com.htf.diva.callBack.IListItemClickListener
import kotlinx.android.synthetic.main.dashborad_fragment.view.*


class DashBoardFragment : BaseFragment<DashboardViewModel>(DashboardViewModel::class.java),
    SwipeRefreshLayout.OnRefreshListener,IListItemClickListener<Any> {
    private lateinit var binding: DashboradFragmentBinding
    private lateinit var currActivity: Activity
    private lateinit var navController: NavController
    private var isProgressBar = true
    private lateinit var dashBoardCategoryAdapter: DashBoardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        currActivity = requireActivity()
        binding = DataBindingUtil.inflate(inflater, R.layout.dashborad_fragment, container, false)
        navController = findNavController()
        setListener()
        binding.dashBoardViewModel = viewModel
        viewModel.categoryList(isProgressBar = isProgressBar)
        viewModelInitialize()


        return binding.root
    }


    private fun setListener() {
        binding.root.refresh.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        try {
            isProgressBar = false
            viewModel.categoryList(isProgressBar = isProgressBar)
            viewModelInitialize()
        } catch (e: Exception) {

        }
    }


    private fun viewModelInitialize() {
        observerViewModel(viewModel.isApiCalling, this::onHandleShowProgress)
        observerViewModel(viewModel.errorResult, this::onHandleApiErrorResponse)
        observerViewModel(viewModel.mDashBoardData, this::onHandleDashBoardSuccessResponse)
    }

    private fun onHandleShowProgress(isNotShow: Boolean) {
        if (isNotShow) progressDialog?.show() else progressDialog?.dismiss()
    }

    private fun onHandleApiErrorResponse(error: String) {
        currActivity.showToast(error, true)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onHandleDashBoardSuccessResponse(dashBoardModel: ArrayList<DashboardModel>?) {
        dashBoardModel?.let {
            if (dashBoardModel.isNotEmpty()) {
                binding.root.refresh.isRefreshing = false
                binding.root.nested_main.visibility = View.VISIBLE
                val mLayout = LinearLayoutManager(currActivity)
                binding.root.rvCategoryList.layoutManager = mLayout
                dashBoardCategoryAdapter = DashBoardAdapter(currActivity, dashBoardModel, this)
                binding.root.rvCategoryList.adapter = dashBoardCategoryAdapter
            } else {
                binding.root.refresh.isRefreshing = false
            }
        }
    }


    override fun onItemClickListener(data: Any) {
        if (data is DashboardModel){
            val bundle = bundleOf(DashBoardAdapter.CATEGORY_MODEL to data)
            findNavController().navigate(R.id.details_dest, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


}