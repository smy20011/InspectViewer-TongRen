package smy20011.reportviewer.ui.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import smy20011.reportviewer.MainActivity
import smy20011.reportviewer.ui.viewholders.BaseViewHolder
import smy20011.reportviewer.ui.viewholders.createViewHolder
import kotlin.reflect.KClass

open class BaseFragment<VH : BaseViewHolder>(val vhClass: KClass<VH>) : Fragment() {
    val viewHolder: VH
        get() = view.tag as VH

    override fun onCreateView(
            inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container!!.createViewHolder(vhClass).view
    }

    fun uiThread(foo: () -> Unit) {
        activity.runOnUiThread(foo)
    }
    fun goto(fragment: Fragment) {
        (activity as MainActivity).goto(fragment, true)
    }
}
