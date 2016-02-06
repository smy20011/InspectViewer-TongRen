package smy20011.reportviewer.model

import android.content.Context
import android.util.Log
import okhttp3.*
import org.jsoup.Jsoup
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import nl.komponents.kovenant.*

enum class ReportFlag {
    Normal, High, Low
}

interface LazyLoad<T> {
    fun load(): Promise<T, Exception>
}

val client = OkHttpClient()

fun get(url : String):Promise<String, Exception> {
    val request = Request.Builder()
            .url(url)
            .build()

    val result = deferred<String, Exception>()

    client.newCall(request).enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            result.resolve(response.body().string())
        }
        override fun onFailure(call: Call?, e: IOException) {
            result.reject(e)
        }
    })

    return result.promise
}

data class ReportItem(
        val name: String,
        val result: String,
        val range: String,
        val flag: ReportFlag
)

data class ReportData(
        val date: Date,
        val category: String,
        val url: String
): LazyLoad<List<ReportItem>> {
    override fun load() =
        get(url) then {
            val doc = Jsoup.parse(it)
            val reportItems = doc
                .select(".jydtable2 tr")
                .drop(1)
                .map {
                    val flag = when (it.child(0).text()) {
                        "H" -> ReportFlag.High
                        "N" -> ReportFlag.Normal
                        else -> ReportFlag.Low
                    }
                    val name = it.child(2).text()
                    val result = it.child(3).text()
                    val range = it.child(4).text()
                    ReportItem(name, result, range, flag)
                }
            reportItems
        }
}

class ReportRepo(val cardNumber: String) : LazyLoad<List<ReportData>> {
    override fun load(): Promise<List<ReportData>, Exception> {
        val current = Calendar.getInstance()
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val end = formatter.format(current.time)
        current.add(Calendar.MONTH, -3)
        val start =  formatter.format(current.time)
        val url = "http://www.shtrhospital.com/onlinework/InspectionSheetList.aspx?id=$cardNumber&begin=$start&end=$end"

        return get(url) then {
            val doc = Jsoup.parse(it)
            val reports =
                doc.select(".jydtable2 tr")
                .drop(1)
                .map {
                    val category = it.child(3).text()
                    val detailedUrl = it.child(4).select("a").attr("href")
                    val pattern = Regex("id=(\\d+)")
                    val receiptNo = pattern.find(detailedUrl)!!.groupValues[1]
                    val datePattern = SimpleDateFormat("yyyyMMdd")
                    val date = datePattern.parse(receiptNo.substring(0..7))
                    ReportData(date, category, "http://www.shtrhospital.com/onlinework/" + detailedUrl)
                }
            reports
        }
    }
}

val Context.reportRepo: ReportRepo
    get() = ReportRepo(this.userInfo.cardNumber)