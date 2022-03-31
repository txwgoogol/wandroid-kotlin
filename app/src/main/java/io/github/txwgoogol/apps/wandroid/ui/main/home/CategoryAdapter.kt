package io.github.txwgoogol.apps.wandroid.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.txwgoogol.apps.wandroid.base.BaseVBViewHolder
import io.github.txwgoogol.apps.wandroid.common.loadmore.BaseVBLoadMoreAdapter
import io.github.txwgoogol.apps.wandroid.databinding.ProjectCategoryItemBinding
import io.github.txwgoogol.apps.wandroid.model.bean.Category

class CategoryAdapter : BaseVBLoadMoreAdapter<Category, ProjectCategoryItemBinding>() {

    var categoryIndex = 0 //点击的下标

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = ProjectCategoryItemBinding.inflate(inflater, parent, false)

    override fun convert(holder: BaseVBViewHolder<ProjectCategoryItemBinding>, item: Category) {
        holder.run {
            vb.category.text = item.name
            vb.category.isSelected = holder.bindingAdapterPosition == categoryIndex
        }
    }

}