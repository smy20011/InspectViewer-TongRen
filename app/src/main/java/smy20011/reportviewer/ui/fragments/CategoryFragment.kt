package smy20011.reportviewer.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import nl.komponents.kovenant.task
import nl.komponents.kovenant.then
import nl.komponents.kovenant.ui.successUi
import smy20011.reportviewer.model.reportRepo
import smy20011.reportviewer.ui.listadapters.getAdapter
import smy20011.reportviewer.ui.viewholders.InfoViewHolder
import smy20011.reportviewer.ui.viewmodels.Category
import smy20011.reportviewer.ui.viewmodels.toCategory

class CategoryFragment : TitleListFragment() {
    val cache = cache(Array<Category>::class.java)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cache load {
            activity.reportRepo.load() then {
                it.toCategory().toTypedArray()
            }
        } then {
            val adapter = it.getAdapter(InfoViewHolder::class) {
                viewHolder, data ->
                viewHolder.left.text = data.name
                viewHolder.right.text = data.reports.size.toString()
                viewHolder.view.setOnClickListener {
                    goto(ReportFragment.getInstance(data))
                }
            }
            uiThread {
                viewHolder.list.adapter = adapter
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        cache.saveToBundle(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        cache.loadFromBundle(savedInstanceState)
        super.onViewStateRestored(savedInstanceState)
    }
}
