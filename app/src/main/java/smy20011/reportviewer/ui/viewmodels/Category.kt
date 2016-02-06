package smy20011.reportviewer.ui.viewmodels

import smy20011.reportviewer.model.ReportData
import smy20011.reportviewer.model.ReportFlag

data class Category(
        val name: String,
        val reports: List<ReportData>
)

fun List<ReportData>.toCategory(): List<Category>
    = this.groupBy { it.category }
          .map { Category(it.key, it.value) }