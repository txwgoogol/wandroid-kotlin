package io.github.txwgoogol.apps.wandroid.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

//懒加载Fragment
abstract class BaseVmFragment<VM : BaseViewModel, T : ViewBinding> : BaseFragment<T>() {

    protected lateinit var viewModel: VM
    private var lazyLoaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        observe()
        initView()
        initData()
    }

    //初始化ViewModel
    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[viewModelClass()]
    }

    //获取ViewModel的class
    abstract fun viewModelClass(): Class<VM>

    override fun onResume() {
        super.onResume()
        if (!lazyLoaded) {
            lazyLoadData()
            lazyLoaded = true
        }
    }

    open fun observe() {
        viewModel.loginStatusInvalid.observe(viewLifecycleOwner, {
            if (it) {
                //登录失效
                //ActivityHelper.startActivity(LoginActivity::class.java)
            }
        })
    }

    //初始化布局
    open fun initView() {}

    //数据初始化相关
    open fun initData() {}

    //懒加载数据
    open fun lazyLoadData() {}

}