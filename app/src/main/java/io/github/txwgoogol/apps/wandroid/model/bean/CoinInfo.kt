package io.github.txwgoogol.apps.wandroid.model.bean

import androidx.annotation.Keep

@Keep
data class CoinInfo(
    val coinCount: Int,
    val level: Int,
    val nickname: String,
    val rank: String,
    val userId: Int,
    val username: String
)