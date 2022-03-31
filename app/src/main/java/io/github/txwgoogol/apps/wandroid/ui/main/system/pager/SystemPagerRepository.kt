package io.github.txwgoogol.apps.wandroid.ui.main.system.pager

import io.github.txwgoogol.apps.wandroid.model.api.RetrofitClient

//体系
class SystemPagerRepository {

    suspend fun treeArticle(page: Int, cid: Int) =
        RetrofitClient.apiService.treeArticle(page, cid).apiData()

    suspend fun treeArticleAuthor(page: Int, author: String) =
        RetrofitClient.apiService.treeArticleAuthor(page, author).apiData()

}