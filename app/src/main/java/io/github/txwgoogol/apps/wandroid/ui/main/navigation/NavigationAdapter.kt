package io.github.txwgoogol.apps.wandroid.ui.main.navigation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.blankj.utilcode.util.ToastUtils
import io.github.txwgoogol.apps.wandroid.base.BaseVBQuickAdapter
import io.github.txwgoogol.apps.wandroid.base.BaseVBViewHolder
import io.github.txwgoogol.apps.wandroid.databinding.NavItemBinding
import io.github.txwgoogol.apps.wandroid.model.bean.Nav

class NavigationAdapter : BaseVBQuickAdapter<Nav, NavItemBinding>() {

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup) =
        NavItemBinding.inflate(inflater, parent, false)

    override fun convert(holder: BaseVBViewHolder<NavItemBinding>, item: Nav) {
        holder.vb.run {
            navTitle.text = item.name
            navTag.run {
                adapter = NavigationTagAdapter(item.articles)
                setOnTagClickListener { _, position, _ ->
                    ToastUtils.showShort(item.articles[position].title)
                    true
                }
            }
        }
    }

}