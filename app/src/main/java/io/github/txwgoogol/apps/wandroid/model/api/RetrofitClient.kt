package io.github.txwgoogol.apps.wandroid.model.api

import io.github.txwgoogol.apps.wandroid.common.core.MoshiHelper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

//网络请求工具
object RetrofitClient {

    //OkHttp
    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(InterceptorEx())
        .build()

    //Retrofit
    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(MoshiHelper.moshi))
        .baseUrl(ApiService.BASE_URL)
        .build()

    //ApiService
    val apiService: ApiService = retrofit.create(ApiService::class.java)

}