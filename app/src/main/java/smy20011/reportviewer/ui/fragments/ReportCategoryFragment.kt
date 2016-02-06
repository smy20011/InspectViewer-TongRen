package smy20011.reportviewer.ui.fragments

import android.content.Context
import android.view.View
import android.widget.ListView
import android.widget.TextView
import nl.komponents.kovenant.then
import smy20011.reportviewer.R
import smy20011.reportviewer.model.reportRepo
import smy20011.reportviewer.model.userInfo
import smy20011.reportviewer.ui.adapters.listAdapter

class ReportCategoryFragment
    : TitleListFragment() {
    override fun dataBind(view: View, ctx: Context) {
        ctx.userInfo.cardNumber = "E10213749"
        val list = view.findViewById(R.id.report_list) as ListView
        ctx.reportRepo.load() then {
            val data = it.groupBy { it -> it.category }
                    .toList()
            val adapter = ctx.listAdapter(R.layout.report_list_item, data) {
                view, data ->
                // Data binding
                val name = view.findViewById(R.id.left) as TextView
                val count = view.findViewById(R.id.right) as TextView
                name.text = data.first
                count.text = data.second.size.toString()
                view.setOnClickListener {
                    val title = data.second.first().category
                    goto(ReportDetailFragment(title, data.second))
                }
            }
            activity.runOnUiThread { list.adapter = adapter }
        }
    }
}