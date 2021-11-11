package com.htf.zbCard.ui.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DashboardModel :Serializable{

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("image")
    @Expose
     var image: String? = null

    @SerializedName("name")
    @Expose
     var name: String? = null


}