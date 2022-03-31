package io.github.txwgoogol.apps.wandroid.ui.main.discovery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.IndicatorGravity
import com.zhpan.indicator.enums.IndicatorStyle
import io.github.txwgoogol.apps.wandroid.R
import io.github.txwgoogol.apps.wandroid.base.BaseVmFragment
import io.github.txwgoogol.apps.wandroid.databinding.DiscoveryFraBinding
import io.github.txwgoogol.apps.wandroid.model.bean.Banner

//发现
class DiscoveryFragment : BaseVmFragment<DiscoveryViewModel, DiscoveryFraBinding>() {

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = DiscoveryFraBinding.inflate(layoutInflater)

    override fun viewModelClass() = DiscoveryViewModel::class.java

    private lateinit var banner: BannerViewPager<Banner>
    private val hotKeyAdapter = HotKeyAdapter()
    private lateinit var tagAdapter: TagAdapter

    override fun initView() {
        super.initView()
        binding.swipeRefresh.run {
            setColorSchemeResources(R.color.colorMain)
            setOnRefreshListener { viewModel.getData() }
        }
        initBanner()
        initHotkeyAdapter()
    }

    //热词
    private fun initHotkeyAdapter() {
        binding.rvHotWord.run {
            //禁止recycler view滚动
            layoutManager = object :GridLayoutManager(activity,3){
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
            hotKeyAdapter.also {
                it.setOnItemClickListener{ _,_,position ->

                }
                adapter = it
            }
        }
    }

    private fun initBanner() {
        banner = activity?.findViewById(R.id.banner) as BannerViewPager<Banner>
        banner.apply {
            adapter = BannerAdapter(this@DiscoveryFragment)
            setLifecycleRegistry(lifecycle)
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            setIndicatorGravity(IndicatorGravity.CENTER)
        }.create()
    }

    override fun observe() {
        super.observe()
        viewModel.banner.observe(viewLifecycleOwner, {
            banner.refreshData(it)
        })
        viewModel.hotkeys.observe(viewLifecycleOwner, {
            hotKeyAdapter.setList(it)
            binding.tvHotWordTitle.isVisible = it.isNotEmpty()
        })
        viewModel.friends.observe(viewLifecycleOwner, {
            binding.tagFlowLayout.run {
                adapter = TagAdapter(it)
                setOnTagClickListener { _, position, _ ->
                    ToastUtils.showShort(it[position].name)

                    false
                }
            }
            binding.tvFrequently.isVisible = it.isNotEmpty()
        })
        viewModel.refreshStatus.observe(viewLifecycleOwner, {
            binding.swipeRefresh.isRefreshing = it
        })
    }

    override fun initData() {
        super.initData()
        viewModel.getData()
    }

}