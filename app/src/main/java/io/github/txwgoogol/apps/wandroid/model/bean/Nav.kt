package io.github.txwgoogol.apps.wandroid.model.bean

import androidx.annotation.Keep

@Keep
data class Nav(
    val articles: MutableList<Article> = mutableListOf(),
    val cid: Int = 0,
    val name: String = ""
)