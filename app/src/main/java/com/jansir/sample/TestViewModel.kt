package com.jansir.sample

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jansir.eventbus.Kobus
import com.jansir.eventbus.core.observeEvent

class TestViewModel : ViewModel() {
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


    fun postStr() {
        Kobus.post("发送str")
    }

    fun postInt() {
        Kobus.post((1..1000).random())
    }

}