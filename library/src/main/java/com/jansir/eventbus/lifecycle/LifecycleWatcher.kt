package com.jansir.eventbus.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.jansir.eventbus.ext.runOnUi

class LifecycleWatcher :ObjectWatcher<LifecycleOwner> {

    private fun LifecycleOwner.newLifecycleEventObserver(block: () -> Unit) =
        object : LifecycleEventObserver {
            override fun onStateChanged(
                source: LifecycleOwner,
                event: Lifecycle.Event
            ) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    block()
                    lifecycle.removeObserver(this)
                }
            }
        }.apply {
            runOnUi { lifecycle.addObserver(this) }
        }

    override fun watch(obj: LifecycleOwner,cb: ObjectWatcher.ObjectState) {
        obj.newLifecycleEventObserver {
            cb.onDestroy()
        }
    }

}