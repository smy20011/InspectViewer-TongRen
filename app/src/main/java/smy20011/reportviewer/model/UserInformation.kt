package smy20011.reportviewer.model

import android.content.Context

class UserInfo(val ctx: Context) {
    val pref = ctx.getSharedPreferences("pref", Context.MODE_PRIVATE)
    var cardNumber: String
        get() = pref.getString("cardNumber", "")
        set(value) {
            pref.edit().putString("cardNumber", value).commit()
        }
}

val Context.userInfo: UserInfo
    get() = UserInfo(this)