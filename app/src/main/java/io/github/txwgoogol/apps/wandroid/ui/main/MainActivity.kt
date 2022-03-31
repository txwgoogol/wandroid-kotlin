package io.github.txwgoogol.apps.wandroid.ui.main

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.core.view.postDelayed
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.blankj.utilcode.util.ToastUtils
import io.github.txwgoogol.apps.wandroid.R
import io.github.txwgoogol.apps.wandroid.base.BaseActivity
import io.github.txwgoogol.apps.wandroid.databinding.MainActBinding

@SuppressLint("CustomSplashScreen")
class MainActivity : BaseActivity<MainActBinding>(),SplashScreen.OnExitAnimationListener {

    private var previousTimeMillis = 0L

    private val viewModel by lazy { MainViewModel() }

    override fun viewBinding() = MainActBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        binding.bottomNavView.setupWithNavController(navHostFragment.navController)


        //返回fragment view重绘
//        val navController = findNavController(R.id.nav_host_fragment_container)
//        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
//        val navigator = FragmentNavigatorExt(this,supportFragmentManager, fragment.id)
//        navController.navigatorProvider.addNavigator(navigator)
//        navController.setGraph(R.navigation.main_nav)
//        binding.bottomNavView.setupWithNavController(navController)
        //


        //
        //val navController = findNavController(R.id.nav_host_fragment_container)

        // get fragment
        //val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)!!

        // setup custom navigator
        //val navigator = KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.nav_host_fragment_container)
        //navController.navigatorProvider.addNavigator(navigator)

        // set navigation graph
        //navController.setGraph(R.navigation.main_nav)

        //binding.bottomNavView.setupWithNavController(navController)
        //


        splashScreen.setKeepVisibleCondition {
            !viewModel.mockDataLoading()
        }
        splashScreen.setOnExitAnimationListener(this)
    }

    override fun onSplashScreenExit(splashScreenViewProvider: SplashScreenViewProvider) {
        //如果在themes.xml中配置了：静态背景, 改成true看效果
        val flag = true
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R || flag) {
            // 使用alpha透明度动画过渡
            val splashScreenView = splashScreenViewProvider.view
            val endAlpha = if(Build.VERSION.SDK_INT < Build.VERSION_CODES.R) 0F else -2F
            val alphaObjectAnimator = ObjectAnimator.ofFloat(splashScreenView, View.ALPHA, 1F, endAlpha)
            alphaObjectAnimator.duration = 500L
            alphaObjectAnimator.interpolator = FastOutLinearInInterpolator()
            alphaObjectAnimator.doOnEnd {
                splashScreenViewProvider.remove()
            }
            alphaObjectAnimator.start()
        }

        //下面是所有使用动态背景的，我们让中心图标做一个动画然后离开
        val splashScreenView = splashScreenViewProvider.view
        val iconView = splashScreenViewProvider.iconView
        val isCompatVersion = Build.VERSION.SDK_INT < Build.VERSION_CODES.R
        val slideUp = ObjectAnimator.ofFloat(iconView, View.TRANSLATION_Y, 0f, -splashScreenView.height.toFloat())
        slideUp.interpolator = AnticipateInterpolator()
        slideUp.duration = if(isCompatVersion) 1000L else 200L
        slideUp.doOnEnd {
            splashScreenViewProvider.remove()
        }
        if (isCompatVersion) {
            //低版本的系统，我们让图标做完动画再关闭
            waitForAnimatedIconToFinish(splashScreenViewProvider, splashScreenView, slideUp)
        } else {
            slideUp.start()
        }
    }

    private fun waitForAnimatedIconToFinish(
        splashScreenViewProvider: SplashScreenViewProvider,
        view: View, animator: Animator) {
        val delayMillis: Long =
            (splashScreenViewProvider.iconAnimationStartMillis + splashScreenViewProvider.iconAnimationDurationMillis) - System.currentTimeMillis()
        view.postDelayed(delayMillis) { animator.start() }
    }

    override fun onBackPressed() {
        val currentTimMillis = System.currentTimeMillis()
        if (currentTimMillis - previousTimeMillis < 2000) {
            super.onBackPressed()
        } else {
            ToastUtils.showShort(R.string.press_again_to_exit)
            previousTimeMillis = currentTimMillis
        }
    }

}

/**
 * 首页：热门文章、最新项目、广场、项目分类、公众号
 * 体系：体系
 * 发现：Banner、搜索热词、常用网站
 * 导航：导航
 * 我的：登录、注册、积分排行、我的积分、我的分享、稍后阅读、我的收藏、浏览历史、关于作者、开源项目、系统设置
 * 详情：文章详情（收藏、分享、浏览器打开、复制链接、刷新页面）
 * 搜索：搜索历史、热门搜索
 * 设置：日夜间模式、调整亮度、字体大小、清除缓存、检查版本、关于玩安卓、退出登录
 */
