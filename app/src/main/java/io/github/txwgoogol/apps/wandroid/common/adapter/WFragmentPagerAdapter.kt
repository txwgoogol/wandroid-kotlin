package io.github.txwgoogol.apps.wandroid.common.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

open class WFragmentPagerAdapter(
    lifecycle: Lifecycle,
    fm: FragmentManager,
    private val fragments: List<Fragment>
) : FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    /**
     * 默认为位置，子类需要覆盖默认，才能保证对应位置的fragment可以改变成其他的fragment，
     * 不然同一位置一直都是最初添加的那个fragment，其他的添加不进去
     * {@link FragmentStatePagerAdapter#getItem(int)}
     */
//    override fun getItemId(position: Int): Long {
//        return fragments[position].hashCode().toLong()
//    }

}