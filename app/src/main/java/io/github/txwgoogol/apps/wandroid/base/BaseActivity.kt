package io.github.txwgoogol.apps.wandroid.base

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ktx.immersionBar
import io.github.txwgoogol.apps.wandroid.common.dialog.ProgressDialogFragment

abstract class BaseActivity<T :ViewBinding> : AppCompatActivity() {

    private lateinit var progressDialogFragment: ProgressDialogFragment

    private lateinit var _binding:T
    protected val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = viewBinding()
        setContentView(_binding.root)
        immersionBar {
            statusBarColor(android.R.color.white)
            statusBarDarkFont(true)
        }
    }

    protected abstract fun viewBinding(): T


    //显示加载(转圈)对话框
    fun showProgressDialog(@StringRes message:Int) {
        if (!this::progressDialogFragment.isInitialized) { //未初始化的初始化
            progressDialogFragment = ProgressDialogFragment.instance()
        }
        if (!progressDialogFragment.isAdded) { //未添加的添加
            progressDialogFragment.show(supportFragmentManager, message, false)
        }
    }

    //隐藏加载(转圈)对话框
    fun dismissProgressDialog() {
        if (this::progressDialogFragment.isInitialized && progressDialogFragment.isVisible) {
            progressDialogFragment.dismissAllowingStateLoss()
        }
    }


}