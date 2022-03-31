package io.github.txwgoogol.apps.wandroid.ui.main.home.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import io.github.txwgoogol.apps.wandroid.R
import io.github.txwgoogol.apps.wandroid.base.BaseVmFragment
import io.github.txwgoogol.apps.wandroid.common.loadmore.setLoadMoreStatus
import io.github.txwgoogol.apps.wandroid.databinding.HomePopularFraBinding
import io.github.txwgoogol.apps.wandroid.ui.main.home.ArticleAdapter

//热门
class PopularFragment : BaseVmFragment<PopularViewModel, HomePopularFraBinding>() {

    companion object {
        fun instance() = PopularFragment()
    }

    private val mAdapter = ArticleAdapter()

    override fun viewModelClass() = PopularViewModel::class.java

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        HomePopularFraBinding.inflate(layoutInflater)

    override fun initView() {
        super.initView()
        binding.swipeRefresh.run {
            setColorSchemeResources(R.color.colorMain)
            setOnRefreshListener { viewModel.refreshArticleList() }
        }
        initListener()
        initAdapter()
    }

    private fun initListener() {
        binding.reloadView.setOnClickListener { viewModel.refreshArticleList() }
    }

    private fun initAdapter() {
        mAdapter.also {
            it.loadMoreModule.setOnLoadMoreListener { viewModel.loadMoreArticleList() }
            binding.rv.adapter = it
        }
    }

    override fun observe() {
        super.observe()
        viewModel.articleList.observe(viewLifecycleOwner, {
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
        //
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        viewModel.refreshArticleList()
    }

}