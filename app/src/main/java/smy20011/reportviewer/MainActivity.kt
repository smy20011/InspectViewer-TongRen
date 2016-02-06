package smy20011.reportviewer

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import smy20011.reportviewer.ui.fragments.ReportCategoryFragment

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentManager.beginTransaction()
        .add(R.id.container, ReportCategoryFragment())
        .commit()
    }

    fun goto(fragment: Fragment) {
        fragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, fragment, null)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}