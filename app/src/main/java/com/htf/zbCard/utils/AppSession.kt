package com.htf.zbCard.utils


import android.provider.Settings
import com.htf.zbCard.base.MyApplication

import java.net.Socket

object AppSession {

    var isRefreshingToken = false
    var locale = "ar"
    var mSocket: Socket? = null
    var userToken = ""
    var deviceId = Settings.Secure.getString(MyApplication.getAppContext().contentResolver,Settings.Secure.ANDROID_ID)
    var deviceType = "android"
    var currency = "SAR"
    var isLocaleEnglish = false
    var userTokenIsValid: Boolean = true
    var mySelectedTab: Int = 0
    var city: String=""
    var state: String=""
    var country: String=""
    var postalCode: String=""
    var orderID=""
    var tokenExpireTime:Int=0
    var isPendingRequestOpen=false
    var latitude = 0.0
    var longitude = 0.0
    var locality = ""
}