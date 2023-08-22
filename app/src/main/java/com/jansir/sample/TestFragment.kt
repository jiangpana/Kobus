package com.jansir.sample

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import com.jansir.eventbus.core.observeEvent

class TestFragment : Fragment() {
    val TAG = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeEvent(String::class.java) {
            Log.d(TAG, "observeEvent: 收到$it")
        }.observeEvent(Int::class.java) {
            Log.d(TAG, "observeEvent: 收到$it")
        }.observeEvent(Long::class.java) {
            Log.d(TAG, "observeEvent: 收到$it")
        }
        postTest()
    }

}