package io.github.txwgoogol.apps.wandroid.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var dataLoaded: Boolean = false

    //启动页 延时时间
    fun mockDataLoading(): Boolean {
        viewModelScope.launch {
            delay(1000)
            dataLoaded = true
        }
        return dataLoaded
    }

}