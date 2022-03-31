package io.github.txwgoogol.apps.wandroid.ui.main.home.wechat

import io.github.txwgoogol.apps.wandroid.model.api.RetrofitClient

//公众号
class WechatRepository {

    suspend fun category() = RetrofitClient.apiService.wxArticle().apiData()

    suspend fun wxArticleList(id: Int, page: Int) =
        RetrofitClient.apiService.wxArticleList(id, page).apiData()

}