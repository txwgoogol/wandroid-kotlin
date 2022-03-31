package io.github.txwgoogol.apps.wandroid.ui.main.profile

import io.github.txwgoogol.apps.wandroid.model.api.RetrofitClient

//个人中心
class ProfileRepository {

    suspend fun userInfo() = RetrofitClient.apiService.userinfo().apiData()

}