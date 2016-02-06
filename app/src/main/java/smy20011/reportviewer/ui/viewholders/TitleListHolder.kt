package smy20011.reportviewer.ui.viewholders

import android.view.View
import smy20011.reportviewer.R

@LayoutRes(R.layout.fragment_title_list)
class TitleListHolder(view: View): BaseViewHolder(view) {
    val title = textView(R.id.title)
    val list  = listView(R.id.list)
}