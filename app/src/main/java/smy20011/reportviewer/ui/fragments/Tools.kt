package smy20011.reportviewer.ui.fragments

import android.os.Bundle
import com.google.gson.Gson
import nl.komponents.kovenant.Promise
import nl.komponents.kovenant.task
import nl.komponents.kovenant.then

val gson = Gson()

fun <T> Bundle.get(clazz: Class<T>)
        = gson.fromJson(this.getString(clazz.name), clazz)

fun <T : Any> Bundle.put(data: T)
        = this.putString(data.javaClass.name, gson.toJson(data))

class Cache<T : Any>(val clazz: Class<T>) {
    private val lock = Object()

    var cache: T? = null

    fun get(): T
            = synchronized(lock) { return cache!! }

    fun exists(): Boolean
            = synchronized(lock) { return cache != null }

    fun put(data: T?)
            = synchronized(lock) { cache = data }

    fun loadFromBundle(bundle: Bundle?) {
        if (bundle == null) {
            return
        }
        val data = bundle.get(clazz)
        put(data)
    }

    fun saveToBundle(bundle: Bundle) {
        if (exists()) {
            bundle.put(get())
        }
    }

    infix fun load(foo: () -> Promise<T, Exception>): Promise<T, Exception> {
        return if (exists()) {
            task { get() }
        } else {
            foo() then {
                put(it)
                it
            }
        }
    }
}

fun <T : Any> cache(clazz: Class<T>) = Cache<T>(clazz)