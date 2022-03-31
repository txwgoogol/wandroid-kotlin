package io.github.txwgoogol.apps.wandroid.ui.main.discovery

import androidx.lifecycle.MutableLiveData
import io.github.txwgoogol.apps.wandroid.base.BaseViewModel
import io.github.txwgoogol.apps.wandroid.model.bean.Banner
import io.github.txwgoogol.apps.wandroid.model.bean.Frequently
import io.github.txwgoogol.apps.wandroid.model.bean.HotKey

//发现
class DiscoveryViewModel : BaseViewModel() {

    private val repository by lazy { DiscoveryRepository() }

    val banner: MutableLiveData<MutableList<Banner>> = MutableLiveData()
    val hotkeys: MutableLiveData<MutableList<HotKey>> = MutableLiveData()
    val friends: MutableLiveData<MutableList<Frequently>> = MutableLiveData()

    val refreshStatus: MutableLiveData<Boolean> = MutableLiveData()
    val reloadStatus: MutableLiveData<Boolean> = MutableLiveData()

    fun getData() {
        launch(
            block = {
                refreshStatus.value = true

                banner.value = repository.banner()
                hotkeys.value = repository.hotWords()
                friends.value = repository.friend()

                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = true
            }
        )
    }

}