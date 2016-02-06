package smy20011.reportviewer.ui.fragments

import android.os.Bundle
import android.view.View
import smy20011.reportviewer.model.ReportFlag
import smy20011.reportviewer.ui.listadapters.getAdapter
import smy20011.reportviewer.ui.viewholders.InfoViewHolder
import smy20011.reportviewer.ui.viewmodels.ReportItem
import java.text.SimpleDateFormat

class DetailedFragment : TitleListFragment() {
    companion object {
        fun getInstance(reportItem: ReportItem): DetailedFragment {
            val fragment = DetailedFragment()
            val bundle = Bundle()
            bundle.put(reportItem)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val data = arguments.get(ReportItem::class.java)
        viewHolder.title.text = "${data.name}\n正常范围: ${data.range}"
        viewHolder.list.adapter = data.histories.getAdapter(InfoViewHolder::class) {
            infoView, data ->
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val resultFlag = when (data.flag) {
                ReportFlag.Normal -> "好"
                ReportFlag.High -> "高"
                ReportFlag.Low -> "低"
            }
            infoView.left.text = formatter.format(data.date)
            infoView.right.text = "${data.result}($resultFlag)"
        }
    }
}