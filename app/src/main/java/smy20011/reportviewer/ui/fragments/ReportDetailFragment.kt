package smy20011.reportviewer.ui.fragments

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import nl.komponents.kovenant.all
import nl.komponents.kovenant.then
import nl.komponents.kovenant.ui.successUi
import smy20011.reportviewer.R
import smy20011.reportviewer.model.ReportData
import smy20011.reportviewer.model.ReportFlag
import smy20011.reportviewer.ui.adapters.listAdapter
import java.util.*

class ReportDetailFragment(val category: String, val reportData: List<ReportData>)
    : TitleListFragment() {

    data class ReportHistory(
            val date: Date,
            val result: String,
            val flag: ReportFlag
    )

    data class ReportItem(
            val name: String,
            val range: String,
            val histories: List<ReportHistory>
    )

    data class ReportSample(
            val name: String,
            val result: String,
            val range: String,
            val flag: ReportFlag,
            val date: Date
    )

    var cachedData: List<ReportItem>? = null

    override fun dataBind(view: View, ctx: Context) {
        title = "$category 检验报告:"
        if (cachedData != null) {
            bindDataToUi(ctx)
            return
        }
        val reportDetailsPromise = reportData.map { it.load() }
        all(reportDetailsPromise) then { reportDetails ->
            val result = reportDetails
                    .zip(reportData)
                    .flatMap {
                        val detail = it.first
                        val report = it.second
                        detail.map { ReportSample(it.name, it.result, it.range, it.flag, report.date) }
                    }
                    .groupBy { it.name }
                    .map {
                        val name = it.key
                        val range = it.value.first().range
                        val histories = it.value
                                .sortedBy { it.date }
                                .map { ReportHistory(it.date, it.result, it.flag) }
                        ReportItem(name, range, histories)
                    }
            Log.d("Report Count", result.toString())
            cachedData = result
            bindDataToUi(ctx)
        } fail {
            Log.d("Failed", "Load Report Failed")
        }
    }

    fun bindDataToUi(ctx: Context) {
        activity.runOnUiThread {
            list.adapter = ctx.listAdapter(R.layout.report_list_item, cachedData!!) {
                view, data ->
                val left = view.findViewById(R.id.left) as TextView
                val right = view.findViewById(R.id.right) as TextView
                left.text = data.name
                right.text = "${data.histories.first().result} -- ${data.histories.last().result}"
                view.setOnClickListener {
                    goto(ReportItemFragment(data))
                }
            }
        }
    }
}