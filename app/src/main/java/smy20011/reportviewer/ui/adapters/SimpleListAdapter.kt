package smy20011.reportviewer.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.BaseAdapter


class SimpleListAdapter<T>(
        val ctx: Context, val resId: Int, val binding : (View, T) -> Unit, val data: List<T>)
    :BaseAdapter() {
    override fun getCount() = data.size

    override fun getView(index: Int, recycledView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(ctx)
        val view = recycledView ?: layoutInflater.inflate(resId, parent, false)
        binding(view, getItem(index))
        return view
    }

    override fun getItem(index: Int) = data[index]
    override fun getItemId(index: Int) = index.toLong()
}

fun <T> Context.listAdapter(resId: Int, data:List<T>, binding: (View, T) -> Unit)
    = SimpleListAdapter<T>(this, resId, binding, data)