package io.github.txwgoogol.apps.jetpack.base

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLoading()
    }

    private fun initLoading() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("正在加载...")
    }

    fun showLoading() {
        progressDialog.show()
    }

    fun hideLoading() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

}