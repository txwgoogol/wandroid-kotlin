package io.github.txwgoogol.apps.wandroid.ui.main.discovery

import android.view.LayoutInflater
import android.view.View
import com.ansen.shape.AnsenTextView
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import io.github.txwgoogol.apps.wandroid.R
import io.github.txwgoogol.apps.wandroid.model.bean.Frequently

class TagAdapter(frequentlyList: MutableList<Frequently>) : TagAdapter<Frequently>(frequentlyList) {

    override fun getView(parent: FlowLayout?, position: Int, t: Frequently): View {
        return LayoutInflater.from(parent?.context)
            .inflate(R.layout.discovery_frequently_item, parent, false)
            .apply {
                findViewById<AnsenTextView>(R.id.frequently).text = t.name
            }
    }

}