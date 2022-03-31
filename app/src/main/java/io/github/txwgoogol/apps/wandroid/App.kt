package io.github.txwgoogol.apps.wandroid

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import io.github.txwgoogol.apps.wandroid.common.core.CoilHelper

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(AndroidLogAdapter())
        instance = this
        CoilHelper.init(instance)
    }

    override fun onTerminate() {
        super.onTerminate()
    }

}