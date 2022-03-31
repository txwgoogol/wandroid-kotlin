package io.github.txwgoogol.apps.wandroid.ui.main.home.plaza

import io.github.txwgoogol.apps.wandroid.model.api.RetrofitClient

class PlazaRepository {

    suspend fun plazaArticleList(page: Int) = RetrofitClient.apiService.articlePlaza(page).apiData()

}