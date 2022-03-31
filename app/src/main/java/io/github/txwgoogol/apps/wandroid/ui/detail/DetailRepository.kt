package io.github.txwgoogol.apps.wandroid.ui.detail

import io.github.txwgoogol.apps.wandroid.model.api.RetrofitClient

//详情
class DetailRepository {

    suspend fun detail() = RetrofitClient.apiService.articleList().apiData()

}