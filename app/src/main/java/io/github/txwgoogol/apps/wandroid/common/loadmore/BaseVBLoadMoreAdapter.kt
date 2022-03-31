package io.github.txwgoogol.apps.wandroid.common.loadmore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import io.github.txwgoogol.apps.wandroid.base.BaseVBViewHolder

abstract class BaseVBLoadMoreAdapter<T, VB : ViewBinding> @JvmOverloads constructor(
    data: MutableList<T>? = null
) : BaseQuickAdapter<T, BaseVBViewHolder<VB>>(0, data), LoadMoreModule {

    abstract fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): VB

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BaseVBViewHolder<VB> {
        val viewBinding = createViewBinding(LayoutInflater.from(parent.context), parent)
        return BaseVBViewHolder(viewBinding, viewBinding.root)
    }

}