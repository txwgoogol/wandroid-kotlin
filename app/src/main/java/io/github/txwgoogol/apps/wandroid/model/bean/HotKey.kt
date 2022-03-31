package io.github.txwgoogol.apps.wandroid.model.bean

import androidx.annotation.Keep

@Keep
data class HotKey(
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)