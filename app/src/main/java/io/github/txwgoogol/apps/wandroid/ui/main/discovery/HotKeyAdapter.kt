package io.github.txwgoogol.apps.wandroid.ui.main.discovery

import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.txwgoogol.apps.wandroid.base.BaseVBQuickAdapter
import io.github.txwgoogol.apps.wandroid.base.BaseVBViewHolder
import io.github.txwgoogol.apps.wandroid.databinding.DiscoveryHotkeyItemBinding
import io.github.txwgoogol.apps.wandroid.model.bean.HotKey

//热词
class HotKeyAdapter : BaseVBQuickAdapter<HotKey, DiscoveryHotkeyItemBinding>() {

    override fun convert(holder: BaseVBViewHolder<DiscoveryHotkeyItemBinding>, item: HotKey) {
        holder.vb.run {
            hotkey.text = item.name
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = DiscoveryHotkeyItemBinding.inflate(inflater, parent, false)

}