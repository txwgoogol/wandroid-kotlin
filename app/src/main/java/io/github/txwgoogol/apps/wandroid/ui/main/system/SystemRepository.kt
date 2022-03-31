package io.github.txwgoogol.apps.wandroid.ui.main.system

import io.github.txwgoogol.apps.wandroid.model.api.RetrofitClient

//体系
class SystemRepository {

    suspend fun tree() = RetrofitClient.apiService.tree().apiData()

}