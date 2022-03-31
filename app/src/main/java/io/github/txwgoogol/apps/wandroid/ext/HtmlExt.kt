package io.github.txwgoogol.apps.wandroid.ext

import androidx.core.text.HtmlCompat

//html格式转换
fun String?.htmlToSpanned() =
    if (this.isNullOrEmpty()) "" else HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)