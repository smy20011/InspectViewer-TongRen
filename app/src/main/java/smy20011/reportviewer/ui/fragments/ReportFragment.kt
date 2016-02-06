package smy20011.reportviewer.ui.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.View
import nl.komponents.kovenant.then
import nl.komponents.kovenant.ui.successUi
import smy20011.reportviewer.ui.listadapters.getAdapter
import smy20011.reportviewer.ui.viewholders.InfoViewHolder
import smy20011.reportviewer.ui.viewmodels.Category
import smy20011.reportviewer.ui.viewmodels.ReportItem
import smy20011.reportviewer.ui.viewmodels.getReportItems

class ReportFragment : TitleListFragment() {
    val cache = cache(Array<ReportItem>::class.java)

    companion object {
        fun getInstance(report: Category): Fragment {
            val fragment = ReportFragment()
            val bundle = Bundle()
            bundle.put(report)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val data = arguments.get(Category::class.java)
        viewHolder.title.text = "检测项目: ${data.name}"
        cache.loadFromBundle(savedInstanceState)
        cache load {
            data.reports.getReportItems() then {it.toTypedArray()}
        } then {
            setUpListAdapter(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        cache.saveToBundle(outState)
        super.onSaveInstanceState(outState)
    }

    fun setUpListAdapter(data: Array<ReportItem>) {
        val adapter = data.getAdapter(InfoViewHolder::class) {
            infoViewHolder, data ->
            val status = if (data.hasAbnormalData) "不正常" else "正常"
            infoViewHolder.left.text = "${data.name}($status)"
            infoViewHolder.right.text = "${data.histories.first().result} -- ${data.histories.last().result}"
            infoViewHolder.view.setOnClickListener {
                goto(DetailedFragment.getInstance(data))
            }
        }
        uiThread {
            viewHolder.list.adapter = adapter
        }
    }
}