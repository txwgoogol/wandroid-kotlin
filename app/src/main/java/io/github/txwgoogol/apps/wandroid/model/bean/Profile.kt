package io.github.txwgoogol.apps.wandroid.model.bean

import androidx.annotation.Keep

@Keep
data class Profile(
    val coinInfo: CoinInfo,
    val userInfo: UserInfo
)