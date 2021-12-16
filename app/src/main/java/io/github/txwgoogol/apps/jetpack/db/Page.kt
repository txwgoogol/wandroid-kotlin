package io.github.txwgoogol.apps.jetpack.db

//分页
data class Page<T>(
    val curPage: Int,
    val datas: List<T>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)