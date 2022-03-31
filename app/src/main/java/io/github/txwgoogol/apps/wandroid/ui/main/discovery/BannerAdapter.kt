package io.github.txwgoogol.apps.wandroid.ui.main.discovery

import androidx.fragment.app.Fragment
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder
import io.github.txwgoogol.apps.wandroid.R
import io.github.txwgoogol.apps.wandroid.common.core.CoilHelper.load
import io.github.txwgoogol.apps.wandroid.databinding.DiscoveryBannerItemBinding
import io.github.txwgoogol.apps.wandroid.model.bean.Banner

class BannerAdapter(var fragment: Fragment) : BaseBannerAdapter<Banner>() {

    override fun bindData(
        holder: BaseViewHolder<Banner>,
        item: Banner,
        position: Int,
        pageSize: Int
    ) {
        val bannerItemBinding = DiscoveryBannerItemBinding.bind(holder.itemView)
        bannerItemBinding.bannerImage.load(lifecycle = fragment.lifecycle, url = item.imagePath)
    }

    override fun getLayoutId(viewType: Int) = R.layout.discovery_banner_item

}