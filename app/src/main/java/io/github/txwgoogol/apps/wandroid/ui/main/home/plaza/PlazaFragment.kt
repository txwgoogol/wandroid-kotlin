package io.github.txwgoogol.apps.wandroid.ui.main.home.plaza

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import io.github.txwgoogol.apps.wandroid.R
import io.github.txwgoogol.apps.wandroid.base.BaseVmFragment
import io.github.txwgoogol.apps.wandroid.common.loadmore.setLoadMoreStatus
import io.github.txwgoogol.apps.wandroid.databinding.HomePlazaFraBinding
import io.github.txwgoogol.apps.wandroid.ui.main.home.ArticleAdapter

//广场
class PlazaFragment : BaseVmFragment<PlazaViewModel, HomePlazaFraBinding>() {

    companion object {
        fun instance() = PlazaFragment()
    }

    override fun viewModelClass() = PlazaViewModel::class.java

    private val mAdapter = ArticleAdapter()

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        HomePlazaFraBinding.inflate(layoutInflater)

    override fun initView() {
        super.initView()
        binding.swipeRefresh.run {
            setColorSchemeResources(R.color.colorMain)
            setOnRefreshListener { viewModel.refreshArticlePlaza() }
        }
        initAdapter()
    }

    private fun initAdapter() {
        mAdapter.also {
            it.loadMoreModule.setOnLoadMoreListener {
                viewModel.loadMoreArticlePlaza()
            }
            binding.rv.adapter = it
        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        viewModel.refreshArticlePlaza()
    }

    override fun observe() {
        super.observe()
        viewModel.articlePlazaList.observe(viewLifecycleOwner, {
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