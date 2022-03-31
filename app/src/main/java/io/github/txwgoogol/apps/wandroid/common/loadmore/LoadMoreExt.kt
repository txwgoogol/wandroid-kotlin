package io.github.txwgoogol.apps.wandroid.common.loadmore

import com.chad.library.adapter.base.module.BaseLoadMoreModule

//自定义加载更多状态
fun BaseLoadMoreModule.setLoadMoreStatus(loadMoreStatus: LoadMoreStatus) {
    when (loadMoreStatus) {
        LoadMoreStatus.COMPLETED -> loadMoreComplete()
        LoadMoreStatus.ERROR -> loadMoreFail()
        LoadMoreStatus.END -> loadMoreEnd()
        else -> return
    }
}