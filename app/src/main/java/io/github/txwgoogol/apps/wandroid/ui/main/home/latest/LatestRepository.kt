package io.github.txwgoogol.apps.wandroid.ui.main.home.latest

import io.github.txwgoogol.apps.wandroid.model.api.RetrofitClient

//最新
class LatestRepository {
    suspend fun articleLatest(page: Int) = RetrofitClient.apiService.projectList(page).apiData()
}