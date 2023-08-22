package com.jansir.sample

import android.os.Handler
import android.os.Looper
import com.jansir.eventbus.Kobus


val handler = Handler(Looper.getMainLooper())

data class TestEvent(val message:String)

fun postTest(){
    handler.postDelayed({
        Kobus.post("post str")
        Kobus.post(TestEvent("post TestEvent message "))
    }, 2000)
}

fun postStickyTest(){
    handler.postDelayed({
        Kobus.postSticky("post sticky str")
        Kobus.postSticky(123456)
        Kobus.postSticky(654321.toLong())
    }, 1100)
}