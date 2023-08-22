package com.jansir.sample

import android.util.Log
import com.jansir.eventbus.core.*

class TestClass : EventScope by MainEventScope() {
    val TAG = javaClass.simpleName

    init {
        observeEvent(String::class.java) {
            Log.d(TAG, "observeEvent: 收到$it")
        }.observeEvent(Int::class.java) {
            Log.d(TAG, "observeEvent: 收到$it")
        }.observeEvent(Long::class.java) {
            Log.d(TAG, "observeEvent: 收到$it")
        }
    }

    fun clear() {
        unregister()
    }
}