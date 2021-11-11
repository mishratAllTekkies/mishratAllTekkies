package com.htf.diva.dashboard.ApiRepo

import com.htf.zbCard.base.BaseResponse
import com.htf.zbCard.ui.home.DashboardModel
import kotlinx.coroutines.Deferred

import retrofit2.Response
import retrofit2.http.*

interface DashBoardApiInterface {

    @GET("categories/list")
    fun dashBoardAsync(): Deferred<Response<BaseResponse<ArrayList<DashboardModel>>>>

   @GET("categories/list")
    fun subCategoryAsync(@Query("parent_id") locale: String?,): Deferred<Response<BaseResponse<ArrayList<DashboardModel>>>>


}

