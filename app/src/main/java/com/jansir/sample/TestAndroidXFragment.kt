package com.jansir.sample

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.jansir.eventbus.core.observeEvent


class TestAndroidXFragment : Fragment() {
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