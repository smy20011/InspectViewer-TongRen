package smy20011.reportviewer.ui.fragments

import android.content.Context
import android.view.View
import android.widget.TextView
import smy20011.reportviewer.R
import smy20011.reportviewer.model.ReportFlag
import smy20011.reportviewer.ui.adapters.listAdapter
import java.text.SimpleDateFormat

class ReportItemFragment(val report: ReportDetailFragment.ReportItem)
    : TitleListFragment() {
    override fun dataBind(view: View, ctx: Context) {
        title = "项目: ${report.name}\n正常范围: ${report.range}"
        list.adapter = ctx.listAdapter(R.layout.report_list_item, report.histories) {
            view, data ->
            val left = view.findViewById(R.id.left) as TextView
            val right = view.findViewById(R.id.right) as TextView
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            left.text = formatter.format(data.date)
            val flagText = when (data.flag) {
                ReportFlag.Normal -> "(好)"
                ReportFlag.High -> "(高)"
                ReportFlag.Low -> "(低)"
            }
            right.text = "${data.result} $flagText"
        }
    }
}