package smy20011.reportviewer.ui.listadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import smy20011.reportviewer.ui.viewholders.BaseViewHolder
import smy20011.reportviewer.ui.viewholders.createViewHolder
import kotlin.reflect.KClass

open class RecycleListAdapter<T, VH: BaseViewHolder>(
        val data: List<T>,
        val vhClass: KClass<VH>,
        val bindFunc : (VH, T) -> Unit
    ): BaseAdapter() {
    override fun getCount(): Int = data.size

    override fun getView(index: Int, recycledView: View?, parent: ViewGroup): View? {
        val data = this.data[index]
        val view = recycledView ?: parent.createViewHolder(vhClass).view
        val viewHolder = view.tag as VH
        bindFunc(viewHolder, data)
        return view
    }

    override fun getItem(p0: Int): Any? = data[p0]
    override fun getItemId(p0: Int): Long = p0.toLong()
}

fun <T, VH: BaseViewHolder> List<T>.getAdapter(vhClass: KClass<VH>, bindFunc: (VH, T) -> Unit)
    = RecycleListAdapter<T, VH>(this, vhClass, bindFunc)


fun <T, VH: BaseViewHolder> Array<T>.getAdapter(vhClass: KClass<VH>, bindFunc: (VH, T) -> Unit)
        = RecycleListAdapter<T, VH>(this.asList(), vhClass, bindFunc)