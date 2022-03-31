package io.github.txwgoogol.apps.wandroid.ui.main.system.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.blankj.utilcode.util.ToastUtils
import io.github.txwgoogol.apps.wandroid.R
import io.github.txwgoogol.apps.wandroid.base.BaseVmFragment
import io.github.txwgoogol.apps.wandroid.common.loadmore.setLoadMoreStatus
import io.github.txwgoogol.apps.wandroid.databinding.SystemFraBinding
import io.github.txwgoogol.apps.wandroid.databinding.SystemPagerFraBinding
import io.github.txwgoogol.apps.wandroid.model.bean.Category
import io.github.txwgoogol.apps.wandroid.ui.main.home.ArticleAdapter
import io.github.txwgoogol.apps.wandroid.ui.main.home.CategoryAdapter

//体系
class SystemPagerFragment : BaseVmFragment<SystemPagerViewModel, SystemPagerFraBinding>() {

    override fun viewModelClass() = SystemPagerViewModel::class.java

    private val mCategoryAdapter = CategoryAdapter()
    private val mAdapter = ArticleAdapter()

    private lateinit var categoryList: MutableList<Category>
    private var checkPosition = 0

    companion object {
        fun instance(treeList: ArrayList<Category>): SystemPagerFragment {
            return SystemPagerFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList("treeList", treeList)
                }
            }
        }
    }

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        SystemPagerFraBinding.inflate(layoutInflater)

    override fun initView() {
        super.initView()
        categoryList = arguments?.getParcelableArrayList("treeList")!!

        binding.swipeRefresh.run {
            setOnRefreshListener { viewModel.refreshArticle(categoryList[checkPosition].id) }
            setColorSchemeResources(R.color.colorMain)
        }
        initCategoryAdapter()
        initAdapter()
    }

    private fun initCategoryAdapter() {
        mCategoryAdapter.also {
            it.setOnItemClickListener { _, _, position ->
                checkPosition = position
                mCategoryAdapter.categoryIndex = checkPosition
                it.notifyDataSetChanged()

                viewModel.refreshArticle(categoryList[position].id)
            }
            it.setList(categoryList)
            binding.rvCategory.adapter = it
        }
    }

    private fun initAdapter() {
        mAdapter.also {
            it.setOnItemClickListener { _, _, position ->
                ToastUtils.showShort(viewModel.articleList.value?.get(position)?.chapterName)
            }
            binding.rv.adapter = it
        }
    }

    override fun observe() {
        super.observe()
        viewModel.articleList.observe(viewLifecycleOwner, {
            mAdapter.setList(it)
        })
        viewModel.loadMoreStatus.observe(viewLifecycleOwner, {
            mAdapter.loadMoreModule.setLoadMoreStatus(it)
        })
        viewModel.refreshStatus.observe(viewLifecycleOwner, {
            binding.swipeRefresh.isRefreshing = it
        })
        viewModel.reloadStatus.observe(viewLifecycleOwner, {
            binding.reloadView.root.isVisible = it
        })
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        viewModel.refreshArticle(categoryList[checkPosition].id)
    }

}