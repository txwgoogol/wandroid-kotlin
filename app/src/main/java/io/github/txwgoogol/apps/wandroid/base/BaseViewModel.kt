package io.github.txwgoogol.apps.wandroid.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.orhanobut.logger.Logger
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import io.github.txwgoogol.apps.wandroid.R
import io.github.txwgoogol.apps.wandroid.model.api.ApiException
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

//类型别名 https://book.kotlincn.net/text/type-aliases.html
typealias Block<T> = suspend (CoroutineScope) -> T
typealias Error = suspend (Exception) -> Unit
typealias Cancel = suspend (Exception) -> Unit

open class BaseViewModel : ViewModel() {

    companion object {
        const val INITIAL_PAGE = 0
        const val INITIAL_PAGE_ONE = 1
        const val INITIAL_CHECKED = 0
    }

    //登录状态
    val loginStatusInvalid: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @param error 错误时执行
     * @param cancel 取消时执行
     * @param showErrorToast 弹出错误提示
     * @return Job
     */
    protected fun launch(
        block: Block<Unit>,
        error: Error? = null,
        cancel: Cancel? = null,
        showErrorToast: Boolean = true,
    ): Job {
        return viewModelScope.launch {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
                        onError(e, showErrorToast)
                        error?.invoke(e)
                    }
                }
            }
        }
    }

    /**
     * 创建并执行协程
     * @param block 执行中协程
     * @return Deferred<T>
     */
    protected fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async { block.invoke(this) }
    }

    /**
     * 取消协程
     * @param job 协程job
     */
    protected fun cancelJob(job: Job?) {
        if (job != null && job.isActive && job.isCompleted && !job.isCancelled) {
            job.cancel()
        }
    }

    /**
     * 统一异常处理
     * @param e 异常
     * @param showErrorToast 显示异常信息提示
     */
    private fun onError(e: Exception, showErrorToast: Boolean) {
        when (e) {
            is ApiException -> {
                when (e.code) {
                    -1001 -> {
                        //登录失效，清除用户信息、清除cookie/token

                        loginStatusInvalid.value = true
                    }
                    //其他api错误
                    -1 -> {
                        Logger.e("其他api错误 ${e.message}")
                        if (showErrorToast) {
                            Logger.d(e.message)
                            //ToastUtils.showShort(e.message)
                        }
                    }
                    //其他未知错误
                    else -> {
                        Logger.e("其他未知错误 ${e.message}")
                        if (showErrorToast) {
                            Logger.d(e.message)
                            //ToastUtils.showShort(e.message)
                        }
                    }
                }
            }
            is ConnectException, //连接被拒绝异常
            is SocketTimeoutException,//服务器响应超时异常
            is UnknownHostException,//无法解析域名异常
            is HttpException,
            is SSLHandshakeException //证书异常
            -> {
                if (showErrorToast) {
                    Logger.e("${R.string.network_request_failed} ${e.message}")
                    //ToastUtils.showShort(R.string.network_request_failed)
                }
            }
            is JsonDataException,
            is JsonEncodingException
            -> {
                if (showErrorToast) {
                    Logger.e("${R.string.api_data_parse_error} ${e.message}")
                    //ToastUtils.showShort(R.string.api_data_parse_error)
                }
            }
            else -> {
                if (showErrorToast) {
                    Logger.e("other ${e.message}")
                    //ToastUtils.showShort(e.message ?: return)
                }
            }
        }
    }

}