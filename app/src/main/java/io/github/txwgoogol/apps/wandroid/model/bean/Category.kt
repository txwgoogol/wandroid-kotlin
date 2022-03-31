package io.github.txwgoogol.apps.wandroid.model.bean

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Category(
    var children: MutableList<Category> = mutableListOf(),
    var courseId: Int = 0,
    var id: Int = 0,
    var name: String = "",
    var order: Int = 0,
    var parentChapterId: Int = 0,
    var userControlSetTop: Boolean = false,
    var visible: Int = 0
) : Parcelable