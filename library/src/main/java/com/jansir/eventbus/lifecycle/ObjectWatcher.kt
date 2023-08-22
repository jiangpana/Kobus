package com.jansir.eventbus.lifecycle

interface ObjectWatcher<T : Any> {

    fun watch(obj: T, cb: ObjectState)

    interface ObjectState {
        fun onDestroy()
    }
}

