package smy20011.reportviewer

import nl.komponents.kovenant.then
import org.junit.Test

import org.junit.Assert.*
import smy20011.reportviewer.model.ReportData
import smy20011.reportviewer.model.ReportRepo
import java.util.*

class ReportTest {
    @Test
    fun loadReport() {
        val loader = ReportRepo("E10213749")
        assert(loader.load().get().size > 0)
    }

    @Test
    fun loadReportDetail() {
        val notify = Object()
        val reportData = ReportData(Date(), "",
                "http://www.shtrhospital.com/onlinework/InspectionSheetResult.aspx?" +
                "id=2016012949000055&begin=2016-01-01&end=2016-02-05&cid=E10213749")

        val data = reportData.load().get()
        assert(data.size > 0)
    }
}