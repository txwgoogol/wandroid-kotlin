package io.github.txwgoogol.apps.wandroid.ui.main.home.project

import io.github.txwgoogol.apps.wandroid.model.api.RetrofitClient

//项目
class ProjectRepository {

    suspend fun projectCategories() = RetrofitClient.apiService.projectCategories().apiData()

    suspend fun projectListByCid(page: Int, cid: Int) =
        RetrofitClient.apiService.projectListByCid(page, cid).apiData()

}