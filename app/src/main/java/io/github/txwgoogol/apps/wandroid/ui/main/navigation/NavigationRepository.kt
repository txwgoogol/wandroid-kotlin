package io.github.txwgoogol.apps.wandroid.ui.main.navigation

import io.github.txwgoogol.apps.wandroid.model.api.RetrofitClient

//导航
class NavigationRepository {

    suspend fun navigation() = RetrofitClient.apiService.nav().apiData()

}