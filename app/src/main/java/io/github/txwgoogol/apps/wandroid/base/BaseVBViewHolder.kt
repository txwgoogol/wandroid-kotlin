package io.github.txwgoogol.apps.wandroid.base

import android.view.View
import androidx.annotation.Keep
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.viewholder.BaseViewHolder

@Keep
open class BaseVBViewHolder<VB : ViewBinding>(val vb: VB, view: View) : BaseViewHolder(view)