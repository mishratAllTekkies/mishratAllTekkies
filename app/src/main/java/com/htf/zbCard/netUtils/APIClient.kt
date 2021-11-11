package com.htf.zbCard.netUtils


import com.htf.zbCard.BuildConfig
import com.htf.zbCard.base.MyApplication
import com.htf.zbCard.netUtils.Constants.Auth.KEY_TOKEN
import com.htf.zbCard.ui.ApiRepo.AuthApiInterface
import com.htf.zbCard.utils.AppPreferences
import com.htf.zbCard.utils.AppSession
import com.htf.zbCard.utils.AppUtils
import com.htf.diva.dashboard.ApiRepo.DashBoardApiInterface
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


object APIClient {

    private fun getClient():Retrofit {
        val logging = HttpLoggingInterceptor()
        if (AppUtils.isAppDebug) {
            logging.level = HttpLoggingInterceptor.Level.HEADERS
            logging.level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val builder = originalRequest.newBuilder()
                    .header("X-Requested-With", "XMLHttpRequest")
                    .header("locale",AppSession.locale)
                val userToken = AppPreferences.getInstance(MyApplication.getInstance()).getFromPreference(KEY_TOKEN)
                if (userToken.isNotEmpty()) {
                    if (userToken.length > 5) {
                        builder.header("Authorization", "Bearer $userToken")
                    }
                } else if (AppSession.userToken.length > 5) {
                    builder.header("Authorization", "Bearer " + AppSession.userToken)
                }
                val newRequest = builder.build()
                chain.proceed(newRequest)
            }
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
        val appUrl = if (BuildConfig.DEBUG) Constants.Urls.DEBUG_BASE_URL else Constants.Urls.BASE_URL
        return Retrofit.Builder()
            .baseUrl(appUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .client(okHttpClient)
            .build()
    }
    val authApiClient: AuthApiInterface = getClient().create(AuthApiInterface::class.java)
    val dashboardApiClient: DashBoardApiInterface = getClient().create(DashBoardApiInterface::class.java)
}