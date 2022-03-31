package io.github.txwgoogol.apps.wandroid.common.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import io.github.txwgoogol.apps.wandroid.R
import io.github.txwgoogol.apps.wandroid.databinding.ProgressDialogBinding

//加载(转圈)对话框
class ProgressDialogFragment : DialogFragment() {

    private var messageResId: Int? = null

    companion object {
        fun instance() = ProgressDialogFragment()
    }

    private val binding:ProgressDialogBinding by lazy { ProgressDialogBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.proMessage.text = getString(messageResId ?: R.string.loading)
    }

    fun show(manager: FragmentManager, @StringRes messageResId: Int, isCancelable: Boolean = false) {
        this.messageResId =  messageResId
        this.isCancelable = isCancelable
        try {
            show(manager, "progressDialogFragment")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}