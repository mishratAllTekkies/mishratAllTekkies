package com.htf.zbCard.ui.home

import androidx.lifecycle.MutableLiveData
import com.htf.zbCard.base.BaseViewModel
import com.htf.zbCard.utils.AppSession
import com.htf.zbCard.utils.DialogUtils
import com.htf.diva.dashboard.ApiRepo.DashboardApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel :BaseViewModel() {
    val isApiCalling= MutableLiveData<Boolean>()
    val errorResult= MutableLiveData<String>()
    val mDashBoardData=MutableLiveData<ArrayList<DashboardModel>>()


    fun categoryList(isProgressBar:Boolean) {
        if (!DialogUtils.isInternetOn()) {
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(isProgressBar)
        scope.launch {
            val result = try {
                DashboardApiRepo.dashBoardAsync(AppSession.locale)
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is ArrayList<*>)
                    mDashBoardData.postValue(result as ArrayList<DashboardModel>)
                else
                    errorResult.postValue(result.toString())
            }
        }
    }


}