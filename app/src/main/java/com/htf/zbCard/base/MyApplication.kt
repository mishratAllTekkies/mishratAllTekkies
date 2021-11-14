package com.htf.zbCard.base

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import com.zynksoftware.documentscanner.ui.DocumentScanner


class MyApplication : Application() {
    /*private var sAnalytics: GoogleAnalytics? = null
    private var sTracker: Tracker? = null*/
    private val FILE_SIZE = 1000000L
    private val FILE_QUALITY = 100
    private val FILE_TYPE = Bitmap.CompressFormat.JPEG

    override fun onCreate() {
        super.onCreate()
        instance = this
        setAppContext(this)

        val configuration = DocumentScanner.Configuration()
        configuration.imageQuality = FILE_QUALITY
        configuration.imageType = FILE_TYPE
        DocumentScanner.init(this, configuration)
    }


    companion object {
        private lateinit var instance: MyApplication
        private lateinit var mAppContext: Context

        fun getInstance(): MyApplication {
            return instance
        }

        fun getAppContext(): Context {
            return mAppContext
        }

        fun setAppContext(mAppContext: Context) {
            Companion.mAppContext = mAppContext
        }
    }
}