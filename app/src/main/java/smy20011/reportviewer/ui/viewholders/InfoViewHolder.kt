package smy20011.reportviewer.ui.viewholders

import android.view.View
import smy20011.reportviewer.R

@LayoutRes(R.layout.report_list_item)
class InfoViewHolder(view: View): BaseViewHolder(view) {
    val left = textView(R.id.left)
    val right = textView(R.id.right)
}