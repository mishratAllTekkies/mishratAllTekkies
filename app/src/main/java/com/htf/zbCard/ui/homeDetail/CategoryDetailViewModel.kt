package com.htf.zbCard.ui.homeDetail

import androidx.lifecycle.MutableLiveData
import com.htf.zbCard.base.BaseViewModel
import com.htf.zbCard.ui.home.DashboardModel
import com.htf.zbCard.utils.DialogUtils
import com.htf.diva.dashboard.ApiRepo.DashboardApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryDetailViewModel :BaseViewModel(){

    val isApiCalling= MutableLiveData<Boolean>()
    val errorResult= MutableLiveData<String>()
    val mCategoryListData= MutableLiveData<ArrayList<DashboardModel>>()


    fun categoryList(parentCategoryId:String) {
        if (!DialogUtils.isInternetOn()) {
            isInternetOn.postValue(false)
            return
        }
        isApiCalling.postValue(true)
        scope.launch {
            val result = try {
                DashboardApiRepo.categoryListAsync(parentCategoryId)
            } catch (e: Exception) {
                errorResult.postValue(e.localizedMessage)
                isApiCalling.postValue(false)
            }
            withContext(Dispatchers.Main) {
                isApiCalling.postValue(false)
                if (result is ArrayList<*>)
                    mCategoryListData.postValue(result as ArrayList<DashboardModel>)
                else
                    errorResult.postValue(result.toString())
            }
        }
    }


}