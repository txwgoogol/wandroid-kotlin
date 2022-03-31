package io.github.txwgoogol.apps.wandroid.model.bean

import androidx.annotation.Keep

@Keep
data class Pagination<T>(
    val curPage: Int,
    val datas: MutableList<T>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)