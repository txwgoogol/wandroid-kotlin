package io.github.txwgoogol.apps.wandroid.ext

import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

//https://blog.csdn.net/Bowentm/article/details/117990705
//重写navigation，实现每次返回原来的fragment view都会重绘的问题 --- 不会用,,,
@Navigator.Name("fragment")
class FragmentNavigatorExt(context: Context, manager: FragmentManager, conditionId: Int) :
    FragmentNavigator(context, manager, conditionId) {

    private var mContext = context
    private var mManager = manager
    private var mConditionId = conditionId

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {

        if (mManager.isStateSaved) return null
        var className = destination.className
        if (className == ".") {
            className = mContext.packageName + className
        }
        val ft = mManager.beginTransaction()
        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1

        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popEnterAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }
        val fragment = mManager.primaryNavigationFragment
        if (fragment != null) {
            ft.hide(fragment)
        }
        var frag: Fragment?
        val tag = destination.id.toString()
        frag = mManager.findFragmentByTag(tag)
        if (frag != null) {
            ft.show(frag)
        } else {
            frag = instantiateFragment(mContext, mManager, className, args)
            frag.arguments = args
            ft.add(mConditionId, frag, tag)
        }
        ft.setPrimaryNavigationFragment(frag)

        @IdRes val destId = destination.id

        val mBackStacks: ArrayDeque<Int>
        val field =
            androidx.navigation.fragment.FragmentNavigator::class.java.getDeclaredField("mBackStack")
        field.isAccessible = true
        mBackStacks = field.get(this) as ArrayDeque<Int>

        val initialNavigation = mBackStacks.isEmpty()
        val isSingleTopReplacement = (navOptions != null && !initialNavigation
                && navOptions.shouldLaunchSingleTop()
                && mBackStacks.last() == destId)


        val isAdded: Boolean
        if (initialNavigation) {
            isAdded = true
        } else if (isSingleTopReplacement) {
            if (mBackStacks.size > 1) {
                mManager.popBackStack(
                    generateBackStackName(mBackStacks.size, mBackStacks.last()),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }
            isAdded = false
        } else {
            ft.addToBackStack(generateBackStackName(mBackStacks.size + 1, destId))
            isAdded = true
        }

        if (navigatorExtras is Extras) {
            val extras = navigatorExtras as Extras?
            for ((key, value) in extras!!.sharedElements) {
                ft.addSharedElement(key, value)
            }
        }
        ft.setReorderingAllowed(true)
        ft.commit()
        return if (isAdded) {
            mBackStacks.add(destId)
            destination
        } else {
            null
        }
    }

    //在父类是 private的  直接定义一个方法即可
    private fun generateBackStackName(backIndex: Int, destId: Int): String {
        return "$backIndex - $destId"
    }

}