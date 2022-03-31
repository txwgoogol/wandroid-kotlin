package io.github.txwgoogol.apps.wandroid.model.bean

import androidx.annotation.Keep

@Keep
data class Login(
    var username: String = "",
    var password: String = ""
)