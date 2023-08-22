package com.jansir.sample

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.jansir.eventbus.core.observeEvent
import kotlinx.coroutines.*

class TestClassActivity : Activity(),CoroutineScope by  MainScope(){
    val TAG = javaClass.simpleName


    var test: TestClass? = TestClass()

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

    override fun onDestroy() {
        super.onDestroy()
        test?.clear()
    }


}

