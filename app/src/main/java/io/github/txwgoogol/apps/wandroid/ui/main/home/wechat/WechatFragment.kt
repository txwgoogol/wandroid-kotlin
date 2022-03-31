package io.github.txwgoogol.apps.wandroid.ui.main.home.wechat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import io.github.txwgoogol.apps.wandroid.R
import io.github.txwgoogol.apps.wandroid.base.BaseVmFragment
import io.github.txwgoogol.apps.wandroid.common.loadmore.setLoadMoreStatus
import io.github.txwgoogol.apps.wandroid.databinding.HomeWechatFraBinding
import io.github.txwgoogol.apps.wandroid.ui.main.home.ArticleAdapter
import io.github.txwgoogol.apps.wandroid.ui.main.home.CategoryAdapter

//公众号
class WechatFragment : BaseVmFragment<WechatViewModel, HomeWechatFraBinding>() {

    companion object {
        fun instance() = WechatFragment()
    }

    private val mAdapter = ArticleAdapter()
    private val mCategoryAdapter = CategoryAdapter()

    override fun viewModelClass() = WechatViewModel::class.java

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        HomeWechatFraBinding.inflate(layoutInflater)

    override fun initView() {
        super.initView()
        binding.swipeRefresh.run {
            setColorSchemeResources(R.color.colorMain)
            setOnRefreshListener { viewModel.refreshArticle() }
        }
        initListener()
        initChapterAdapter()
        initAdapter()
    }

    private fun initListener() {
        binding.reloadView.root.setOnClickListener { viewModel.refreshArticle() }
        binding.reloadListView.root.setOnClickListener { viewModel.refreshArticle() }
    }

    private fun initChapterAdapter() {
        mCategoryAdapter.also {
            it.setOnItemClickListener { _, _, position ->

            }
            binding.rvCategory.adapter = it
        }
    }

    private fun initAdapter() {
        mAdapter.also {
            it.loadMoreModule.setOnLoadMoreListener { viewModel.loadMoreArticle() }
            binding.rv.adapter = it
        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        viewModel.firstArticle()
    }

    override fun observe() {
        super.observe()
        viewModel.wechatList.observe(viewLifecycleOwner,{
            mCategoryAdapter.setList(it)
        })
        viewModel.articleList.observe(viewLifecycleOwner, {
            mAdapter.setList(it)
        })
        viewModel.refreshStatus.observe(viewLifecycleOwner, {
            binding.swipeRefresh.isRefreshing = it
        })
        viewModel.reloadStatus.observe(viewLifecycleOwner, {
            binding.reloadView.root.isVisible = it
        })
        viewModel.reloadListStatus.observe(viewLifecycleOwner, {
            binding.reloadListView.root.isVisible = it
        })
        viewModel.loadMoreStatus.observe(viewLifecycleOwner, {
            mAdapter.loadMoreModule.setLoadMoreStatus(it)
        })
    }

}