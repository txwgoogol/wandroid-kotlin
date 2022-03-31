package io.github.txwgoogol.apps.wandroid.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import io.github.txwgoogol.apps.wandroid.R
import io.github.txwgoogol.apps.wandroid.base.BaseFragment
import io.github.txwgoogol.apps.wandroid.common.adapter.WFragmentPagerAdapter
import io.github.txwgoogol.apps.wandroid.databinding.HomeFraBinding
import io.github.txwgoogol.apps.wandroid.ui.main.home.latest.LatestFragment
import io.github.txwgoogol.apps.wandroid.ui.main.home.plaza.PlazaFragment
import io.github.txwgoogol.apps.wandroid.ui.main.home.popular.PopularFragment
import io.github.txwgoogol.apps.wandroid.ui.main.home.project.ProjectFragment
import io.github.txwgoogol.apps.wandroid.ui.main.home.wechat.WechatFragment

//首页
class HomeFragment : BaseFragment<HomeFraBinding>() {

    private lateinit var fragments: List<Fragment>

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        HomeFraBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titles = listOf<CharSequence>(
            getString(R.string.popular),
            getString(R.string.latest),
            getString(R.string.plaza),
            getString(R.string.project),
            getString(R.string.wechat)
        )
        fragments = listOf<Fragment>(
            PopularFragment.instance(),
            LatestFragment.instance(),
            PlazaFragment.instance(),
            ProjectFragment.instance(),
            WechatFragment.instance()
        )

        binding.viewPager.run {
            offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT //禁用预加载
            adapter = WFragmentPagerAdapter(lifecycle,childFragmentManager,fragments)
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position -> tab.text = titles[position] }.attach()

    }

}