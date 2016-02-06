package smy20011.reportviewer.ui.fragments

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import smy20011.reportviewer.MainActivity
import smy20011.reportviewer.R

open abstract class TitleListFragment : Fragment() {
    abstract fun dataBind(view: View, ctx: Context)
    override fun onCreateView
            (inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_title_list, container, false)
        fragmentView = view
        dataBind(view, container.context)
        return view
    }

    override fun onPause() {
        fragmentView = null
        super.onPause()
    }

    fun goto(fragment: Fragment) {
        (activity as MainActivity).goto(fragment)
    }

    private var fragmentView: View? = null
    private val titleView: TextView
        get() = fragmentView?.findViewById(R.id.title) as TextView

    var title: String
        get() = titleView.text.toString()
        set(value) = titleView.setText(value)

    val list: ListView
        get() = fragmentView?.findViewById(R.id.report_list) as ListView
}