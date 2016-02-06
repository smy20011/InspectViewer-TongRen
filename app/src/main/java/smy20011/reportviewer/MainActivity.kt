package smy20011.reportviewer

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import smy20011.reportviewer.ui.fragments.CategoryFragment

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) goto(CategoryFragment())
    }

    fun goto(fragment: Fragment, backStack: Boolean = false) {
        val transaction = fragmentManager.beginTransaction()
        if (backStack) transaction.addToBackStack(null)
        transaction.replace(R.id.container, fragment, null)
        transaction.commit()
    }
}