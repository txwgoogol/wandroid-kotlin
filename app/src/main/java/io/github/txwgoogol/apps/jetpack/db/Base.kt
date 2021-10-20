package io.github.txwgoogol.apps.jetpack.db

data class Base<T>(
    val `data`: T,
    val errorCode: Int,
    val errorMsg: String
)