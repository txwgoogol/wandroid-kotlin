package io.github.txwgoogol.apps.wandroid.ui.main.navigation

import androidx.lifecycle.MutableLiveData
import io.github.txwgoogol.apps.wandroid.base.BaseViewModel
import io.github.txwgoogol.apps.wandroid.model.bean.Nav

//导航
class NavigationViewModel : BaseViewModel() {

    private val repository by lazy { NavigationRepository() }

    val navList: MutableLiveData<MutableList<Nav>> = MutableLiveData()

    val refreshStatus: MutableLiveData<Boolean> = MutableLiveData()
    val reloadStatus: MutableLiveData<Boolean> = MutableLiveData()

    fun nav() {
        launch(
            block = {
                refreshStatus.value = true
                reloadStatus.value = false

                navList.value = repository.navigation()

                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = true
            })
    }

}