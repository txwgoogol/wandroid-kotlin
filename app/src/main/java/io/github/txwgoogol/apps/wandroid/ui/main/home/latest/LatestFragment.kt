package io.github.txwgoogol.apps.wandroid.ui.main.home.latest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import io.github.txwgoogol.apps.wandroid.R
import io.github.txwgoogol.apps.wandroid.base.BaseVmFragment
import io.github.txwgoogol.apps.wandroid.common.loadmore.setLoadMoreStatus
import io.github.txwgoogol.apps.wandroid.databinding.HomeLatestFraBinding
import io.github.txwgoogol.apps.wandroid.ui.main.home.ArticleAdapter

//最新
class LatestFragment : BaseVmFragment<LatestViewModel, HomeLatestFraBinding>() {

    companion object {
        fun instance() = LatestFragment()
    }

    private val mAdapter = ArticleAdapter()

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        HomeLatestFraBinding.inflate(layoutInflater)

    override fun viewModelClass() = LatestViewModel::class.java

    override fun initView() {
        super.initView()
        binding.swipeRefresh.run {
            setColorSchemeResources(R.color.colorMain)
            setOnRefreshListener { viewModel.refreshLatestArticleList() }
        }
        initAdapter()
    }

    private fun initAdapter() {
        mAdapter.also {
            it.loadMoreModule.setOnLoadMoreListener {
                viewModel.loadMoreArticleList()
            }
            binding.rv.adapter = it
        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        viewModel.refreshLatestArticleList()
    }

    override fun observe() {
        super.observe()
        viewModel.articleLatestList.observe(viewLifecycleOwner, {
            mAdapter.setList(it)
        })
        viewModel.refreshStatus.observe(viewLifecycleOwner, {
            binding.swipeRefresh.isRefreshing = it
        })
        viewModel.loadMoreStatus.observe(viewLifecycleOwner, {
            mAdapter.loadMoreModule.setLoadMoreStatus(it)
        })
        viewModel.reloadStatus.observe(viewLifecycleOwner, {
            binding.reloadView.isVisible = it
        })
    }

}