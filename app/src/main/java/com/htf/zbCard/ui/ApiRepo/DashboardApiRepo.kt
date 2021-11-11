package com.htf.diva.dashboard.ApiRepo


import com.htf.zbCard.base.BaseRepository
import com.htf.zbCard.netUtils.APIClient



object DashboardApiRepo : BaseRepository() {

    private val retrofitAuthClient= APIClient.dashboardApiClient

    suspend fun dashBoardAsync(locale :String): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.dashBoardAsync().await()}
        )
    }


    suspend fun categoryListAsync(parentCategoryId:String): Any? {
        return safeApiCall(
            call ={ retrofitAuthClient.subCategoryAsync(parentCategoryId).await()}
        )
    }

}









