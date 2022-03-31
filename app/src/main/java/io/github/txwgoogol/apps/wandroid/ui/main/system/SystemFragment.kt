package io.github.txwgoogol.apps.wandroid.ui.main.system

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.Logger
import io.github.txwgoogol.apps.wandroid.base.BaseVmFragment
import io.github.txwgoogol.apps.wandroid.common.adapter.WFragmentPagerAdapter
import io.github.txwgoogol.apps.wandroid.databinding.SystemFraBinding
import io.github.txwgoogol.apps.wandroid.model.bean.Category
import io.github.txwgoogol.apps.wandroid.ui.main.system.pager.SystemPagerFragment

//体系
class SystemFragment : BaseVmFragment<SystemViewModel, SystemFraBinding>() {

    private val fragments = mutableListOf<Fragment>()
    private val titles = mutableListOf<CharSequence>()

    override fun viewModelClass() = SystemViewModel::class.java

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        SystemFraBinding.inflate(layoutInflater)

    override fun observe() {
        super.observe()
        viewModel.treeList.observe(viewLifecycleOwner, { it ->

            it.forEach {
                titles.add(it.name)
                fragments.add(SystemPagerFragment.instance(it.children as ArrayList<Category>))
            }
            Logger.d("titles=${titles.size} fragments=${fragments.size}")
            binding.viewPager.run {
                offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT //禁用预加载
                adapter = WFragmentPagerAdapter(lifecycle, childFragmentManager, fragments)
            }
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = titles[position]
            }.attach()

        })
        viewModel.reloadStatus.observe(viewLifecycleOwner,{
            binding.reloadView.root.isVisible = it
        })
    }

    override fun initData() {
        super.initData()
        viewModel.tree()
    }

}