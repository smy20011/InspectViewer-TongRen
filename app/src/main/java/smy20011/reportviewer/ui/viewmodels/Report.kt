package smy20011.reportviewer.ui.viewmodels

import nl.komponents.kovenant.Promise
import nl.komponents.kovenant.all
import nl.komponents.kovenant.then
import smy20011.reportviewer.model.ReportData
import smy20011.reportviewer.model.ReportFlag
import java.util.*

data class ReportHistory (
        val date: Date,
        val result: String,
        val flag: ReportFlag
)

data class ReportItem(
        val name: String,
        val range: String,
        val hasAbnormalData: Boolean,
        val histories: List<ReportHistory>
)

data class TempReportItem(
        val name: String,
        val range: String,
        val result: String,
        val flag: ReportFlag,
        val date: Date
)

fun List<ReportData>.getReportItems(): Promise<List<ReportItem>, Exception> {
    val data = this
    val detailedData = this.map {
        it.load()
    }
    return all(detailedData) then {
        it.zip(data).flatMap {
            val date = it.second.date
            it.first.map {
                TempReportItem(it.name, it.range, it.result, it.flag, date)
            }
        }.groupBy {
            it.name
        }.map {
            val histories =
                    it.value.map { ReportHistory(it.date, it.result, it.flag) }.sortedBy { it.date }
            ReportItem(it.key, it.value.first().range, histories.any { it.flag != ReportFlag.Normal }, histories)
        }.sortedByDescending { it.hasAbnormalData }
    }
}