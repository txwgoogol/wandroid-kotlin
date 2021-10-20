package io.github.txwgoogol.apps.jetpack.api

import io.github.txwgoogol.apps.jetpack.db.Base
import io.github.txwgoogol.apps.jetpack.db.Page
import io.github.txwgoogol.apps.jetpack.db.home.Article
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

//接口
interface ApiStores {

    /**
     * 首页文章
     * https://www.wanandroid.com/article/list/0/json
     * 方法：GET
     * 参数：页码，拼接在连接中，从0开始。
     */
    @GET("article/list/{index}/json")
    suspend fun homeArticle(@Path("index") id: String): Result<Base<Page<Article>>>

    companion object {
        private const val BASE_URL = "https://www.wanandroid.com/"
        fun create(): ApiStores {
            val client = OkHttpClient
                .Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(InterceptorEx())
                .build()
            return Retrofit.Builder().baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiStores::class.java)
        }
    }

}