package com.jansir.eventbus.ext

import android.os.Handler
import android.os.Looper


val uiHandler = Handler(Looper.getMainLooper())

fun runOnUi(block: () -> Unit) {
    if (!isRunOnUiThread()) {
        uiHandler.post { block() }
    } else {
        block()
    }
}

fun isRunOnUiThread() = Looper.getMainLooper() == Looper.myLooper()

const val DEFAULT_KEY = "com.jansir.bus.key_"
const val DEFAULT_INT_KEY = "int"
fun getEventKeyFormClazz(event: Class<*>): String {
    val clazzName =
        if (event.simpleName == Integer::class.java.simpleName || event.simpleName === Int::class.java.simpleName) {
            DEFAULT_INT_KEY
        } else {
            event.simpleName.lowercase()
        }
   return DEFAULT_KEY + clazzName
}

val hasEventBus by lazy {
    try {
        Class.forName("org.greenrobot.eventbus.EventBus")
    } catch (e: ClassNotFoundException) {
       return@lazy false
    }
    true
}

val hasKotlinFlow by lazy {
    try {
        Class.forName("kotlinx.coroutines.flow.MutableSharedFlow")
    } catch (e: ClassNotFoundException) {
        return@lazy false
    }
    true
}
