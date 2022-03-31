package io.github.txwgoogol.apps.wandroid.ui.main.navigation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.txwgoogol.apps.wandroid.R
import io.github.txwgoogol.apps.wandroid.base.BaseVmFragment
import io.github.txwgoogol.apps.wandroid.databinding.NavigationFraBinding

//导航
class NavigationFragment : BaseVmFragment<NavigationViewModel, NavigationFraBinding>() {

    private var currentPosition = 0

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = NavigationFraBinding.inflate(layoutInflater)

    override fun viewModelClass() = NavigationViewModel::class.java

    private val mAdapter = NavigationAdapter()

    override fun initView() {
        super.initView()
        binding.swipeRefresh.run {
            setOnRefreshListener { viewModel.nav() }
            setColorSchemeResources(R.color.colorMain)
        }

        //float title setting !!!
        binding.tvFloatTitle.run {
            binding.rv.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                if (scrollY < oldScrollY) {
                    text = mAdapter.data[currentPosition].name
                }
                val lm = binding.rv.layoutManager as LinearLayoutManager
                val nextView = lm.findViewByPosition(currentPosition + 1)
                if (nextView != null) {
                    y = if (nextView.top < measuredHeight) {
                        (nextView.top - measuredHeight).toFloat()
                    } else {
                        0f
                    }
                }
                currentPosition = lm.findFirstVisibleItemPosition()
                if (scrollY > oldScrollY) {
                    text = mAdapter.data[currentPosition].name
                }
            }
        }
        initAdapter()
    }

    private fun initAdapter() {
        mAdapter.also {
            binding.rv.adapter = it
        }
    }

    override fun observe() {
        super.observe()
        viewModel.navList.observe(viewLifecycleOwner, {
            binding.tvFloatTitle.run {
                isGone = it.isEmpty()
                text = it[0].name
            }
            mAdapter.setList(it)
        })
        viewModel.refreshStatus.observe(viewLifecycleOwner, {
            binding.swipeRefresh.isRefreshing = it
        })
        viewModel.reloadStatus.observe(viewLifecycleOwner, {
            binding.reloadView.root.isVisible = it
        })
    }

    override fun initData() {
        super.initData()
        viewModel.nav()
    }

}