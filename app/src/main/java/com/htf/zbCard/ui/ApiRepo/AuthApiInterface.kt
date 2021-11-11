package com.htf.zbCard.ui.ApiRepo

import com.htf.zbCard.base.BaseResponse
import com.htf.zbCard.ui.home.DashboardModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApiInterface  {
    @FormUrlEncoded
    @POST("api/v1/app/settings")
    fun appSettingAsync(
        @Field("locale") locale:String,
        @Field("device_id")device_id:String,
        @Field("device_type")device_type:String,
        @Field("current_version")current_version:String): Deferred<Response<BaseResponse<DashboardModel>>>

    @FormUrlEncoded
    @POST("api/v1/refresh/token")
    fun userRefreshTokenAsync(@Field("locale") locale:String,
                              @Field("device_id")device_id:String,
                              @Field("device_type")device_type:String,
                              @Field("current_version")current_version:String): Deferred<Response<BaseResponse<DashboardModel>>>

}