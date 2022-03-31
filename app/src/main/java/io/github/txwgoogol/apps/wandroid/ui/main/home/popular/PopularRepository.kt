package io.github.txwgoogol.apps.wandroid.ui.main.home.popular

import io.github.txwgoogol.apps.wandroid.model.api.RetrofitClient

//热门
class PopularRepository {
    suspend fun articleTop() = RetrofitClient.apiService.articleTop().apiData()
    suspend fun articleList(page: Int) = RetrofitClient.apiService.articleList(page).apiData()
}