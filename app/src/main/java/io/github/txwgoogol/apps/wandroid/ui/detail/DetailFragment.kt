package io.github.txwgoogol.apps.wandroid.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.txwgoogol.apps.wandroid.base.BaseVmFragment
import io.github.txwgoogol.apps.wandroid.databinding.DetailFraBinding

class DetailFragment : BaseVmFragment<DetailViewModel, DetailFraBinding>() {

    override fun viewModelClass() = DetailViewModel::class.java

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        DetailFraBinding.inflate(inflater, container, false)

    override fun lazyLoadData() {
        super.lazyLoadData()
    }

}