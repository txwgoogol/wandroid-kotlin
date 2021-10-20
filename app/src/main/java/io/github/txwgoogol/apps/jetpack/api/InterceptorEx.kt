package io.github.txwgoogol.apps.jetpack.api

import com.blankj.utilcode.util.SPStaticUtils
import com.orhanobut.logger.Logger
import okhttp3.Interceptor
import okhttp3.Response

//请求数据拦截
class InterceptorEx : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        //添加公共请求头
        val request = chain.request().newBuilder()
            .header("CPC-TOKEN", SPStaticUtils.getString("uid"))
            .method(original.method, original.body)
            .build()

        //请求前--打印请求信息
        val t1 = System.nanoTime()
        Logger.d(String.format("Sending request %s on %s%n%s", request.url, chain.connection(), request.headers))

        //网络请求
        val response = chain.proceed(request)

        //网络响应后--打印响应信息
        val t2 = System.nanoTime()
        Logger.d(String.format("Received response for %s in %.1fms%n%s", response.request.url, (t2 - t1) / 1e6, response.headers))
        return response
    }

}