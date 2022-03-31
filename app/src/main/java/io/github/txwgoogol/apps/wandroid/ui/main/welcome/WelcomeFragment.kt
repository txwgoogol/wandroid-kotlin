package io.github.txwgoogol.apps.wandroid.ui.main.welcome

import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.txwgoogol.apps.wandroid.base.BaseFragment
import io.github.txwgoogol.apps.wandroid.databinding.WelcomeFraBinding

//欢迎页
class WelcomeFragment : BaseFragment<WelcomeFraBinding>() {

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        WelcomeFraBinding.inflate(layoutInflater)

}