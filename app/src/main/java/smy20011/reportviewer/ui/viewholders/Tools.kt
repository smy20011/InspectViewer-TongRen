package smy20011.reportviewer.ui.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import kotlin.reflect.KClass

open class BaseViewHolder(val view: View) {
    fun textView(id: Int) = view.findViewById(id) as TextView
    fun listView(id: Int) = view.findViewById(id) as ListView
}

@Retention(AnnotationRetention.RUNTIME)
annotation class LayoutRes(val layoutRes: Int)

fun <VH: Any> createViewHolder(view: View, vhClass: KClass<VH>)
    = vhClass.constructors.first().call(view)

fun <VH: Any> ViewGroup.createViewHolder(vhClass: KClass<VH>): VH {
    val inflater = LayoutInflater.from(this.context)
    val annotation = vhClass.annotations.first { it.annotationClass == LayoutRes::class }
    val resId = (annotation as LayoutRes).layoutRes
    val view = inflater.inflate(resId, this, false)
    val vh = vhClass.constructors.first().call(view)
    view.tag = vh
    return vh
}