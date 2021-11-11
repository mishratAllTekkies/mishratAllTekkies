package com.htf.zbCard.netUtils

object Constants {

    object Urls{
        var BASE_URL = "http://htf.sa/brandic/api/user/"
        var DEBUG_BASE_URL =/* "http://157.175.84.232/"*/BASE_URL
        val IMAGE_URL="http://htf.sa/brandic/uploads/images/"
        val COUNTRY_IMAGE_URL = "$BASE_URL/uploads/country_flags/"
        val PAYROLL_PDF_URL="$BASE_URL/uploads/salarySlip/"
    }

    val KEY_PREF_USER_LANGUAGE="user_lang"
    const val BROADCAST_ACTION ="com.htf.brandic.utils"

    object Auth{
        val KEY_TOKEN = "token"
        val KEY_USER_JSON_DETAILS = "jsonUserDetails"
        val KEY_APP_CONFIG = "jsonAppConfig"
    }


    object Auth_Intent_Actions{
        val BROADCAST_ACTION_BLACKLISTED="user_black_listed"
    }



}