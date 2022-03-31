package io.github.txwgoogol.apps.wandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    private var _binding: T? = null
    val binding: T
        get() = _binding
            ?: throw RuntimeException("Should only use binding after onCreateView and before onDestroyView")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = viewBinding(inflater, container)
        return _binding?.root
    }

    protected abstract fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): T

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}