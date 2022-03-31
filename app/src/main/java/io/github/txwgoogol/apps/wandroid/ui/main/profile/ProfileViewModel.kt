package io.github.txwgoogol.apps.wandroid.ui.main.profile

import androidx.lifecycle.MutableLiveData
import io.github.txwgoogol.apps.wandroid.base.BaseViewModel
import io.github.txwgoogol.apps.wandroid.model.bean.Profile

class ProfileViewModel : BaseViewModel() {

    private val repository by lazy { ProfileRepository() }
    val profile: MutableLiveData<Profile> = MutableLiveData()

    val refreshStatus: MutableLiveData<Boolean> = MutableLiveData()

    fun profile() {
        launch(
            block = {
                refreshStatus.value = true
                profile.value = repository.userInfo()

                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
            })
    }

}