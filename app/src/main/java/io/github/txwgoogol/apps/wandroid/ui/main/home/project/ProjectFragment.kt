package io.github.txwgoogol.apps.wandroid.ui.main.home.project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import io.github.txwgoogol.apps.wandroid.R
import io.github.txwgoogol.apps.wandroid.base.BaseVmFragment
import io.github.txwgoogol.apps.wandroid.common.loadmore.setLoadMoreStatus
import io.github.txwgoogol.apps.wandroid.databinding.HomeProjectFraBinding
import io.github.txwgoogol.apps.wandroid.ui.main.home.ArticleAdapter
import io.github.txwgoogol.apps.wandroid.ui.main.home.CategoryAdapter

//项目
class ProjectFragment : BaseVmFragment<ProjectViewModel, HomeProjectFraBinding>() {

    companion object {
        fun instance() = ProjectFragment()
    }

    private val mCategoryAdapter = CategoryAdapter()
    private val mAdapter = ArticleAdapter()
    override fun viewModelClass() = ProjectViewModel::class.java

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        HomeProjectFraBinding.inflate(layoutInflater)

    override fun initView() {
        super.initView()
        binding.swipeRefresh.run {
            setColorSchemeResources(R.color.colorMain)
            setOnRefreshListener { viewModel.refreshArticleList() }
        }
        initAdapter()
    }

    private fun initAdapter() {
        mCategoryAdapter.also {
            it.setOnItemClickListener { _, _, position ->
                it.categoryIndex = position
                //viewModel.checkCategory.value = position
                //viewModel.refreshArticleList(checkPosition = viewModel.checkCategory.value!!)
                it.notifyDataSetChanged()
            }
            binding.rvCategory.adapter = it
        }
        mAdapter.also {
            it.loadMoreModule.setOnLoadMoreListener { viewModel.loadMoreArticleList() }
            binding.rv.adapter = it
        }
    }

    override fun observe() {
        super.observe()
        viewModel.categories.observe(viewLifecycleOwner, {
            mCategoryAdapter.setList(it)
        })
        viewModel.articleList.observe(viewLifecycleOwner, {
            mAdapter.setList(it)
        })
        viewModel.checkCategory.observe(viewLifecycleOwner, {
            viewModel.checkCategory.value = it
        })
        viewModel.refreshStatus.observe(viewLifecycleOwner, {
            binding.swipeRefresh.isRefreshing = it
        })
        viewModel.reloadStatus.observe(viewLifecycleOwner, {
            binding.reloadView.isVisible = it
        })
        viewModel.reloadListStatus.observe(viewLifecycleOwner, {
            binding.reloadListView.isVisible = it
        })
        viewModel.loadMoreStatus.observe(viewLifecycleOwner, {
            mAdapter.loadMoreModule.setLoadMoreStatus(it)
        })
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        viewModel.projectCategories()
    }

}