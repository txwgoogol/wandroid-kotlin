package io.github.txwgoogol.apps.wandroid.ui.main.discovery

import io.github.txwgoogol.apps.wandroid.model.api.RetrofitClient

//发现
class DiscoveryRepository {

    suspend fun banner() = RetrofitClient.apiService.banner().apiData()

    suspend fun hotWords() = RetrofitClient.apiService.hotkey().apiData()

    suspend fun friend() = RetrofitClient.apiService.friend().apiData()

}