package io.github.txwgoogol.apps.wandroid.ui.main.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.txwgoogol.apps.wandroid.base.BaseVmFragment
import io.github.txwgoogol.apps.wandroid.databinding.HomeProfileFraBinding

//个人中心
class ProfileFragment : BaseVmFragment<ProfileViewModel, HomeProfileFraBinding>() {

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        HomeProfileFraBinding.inflate(layoutInflater)

    override fun viewModelClass() = ProfileViewModel::class.java

    override fun initView() {
        super.initView()

        initListener()
    }

    private fun initListener() {
        binding.clHeader.setOnClickListener {
            //个人信息
        }
        binding.llMyPoints.setOnClickListener {
            //我的积分
        }
        binding.llPointsRank.setOnClickListener {
            //我的积分排名
        }
        binding.llPointsRank.setOnClickListener {
            //我的分享
        }
        binding.llMyCollect.setOnClickListener {
            //我的收藏
        }
        binding.llHistory.setOnClickListener {
            //我的浏览历史
        }

        binding.llOpenSource.setOnClickListener {
            //开源
        }
        binding.llAboutAuthor.setOnClickListener {
            //关于作者
        }
        binding.llSetting.setOnClickListener {
            //设置
        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        viewModel.profile()
    }

    override fun observe() {
        super.observe()
        viewModel.profile.observe(viewLifecycleOwner, {
            binding.tvNickName.text = it.userInfo.nickname
        })
    }

}