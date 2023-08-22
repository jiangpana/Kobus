package com.jansir.eventbus.lifecycle

import android.app.Activity
import android.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelWatcher

class WatcherFactory {

    companion object {
        fun build(obj: Any, cb: ObjectWatcher.ObjectState): ObjectWatcher<*>? {
            val watcher = when (obj) {
                is LifecycleOwner -> LifecycleWatcher()
                is ViewModel -> ViewModelWatcher()
                is Activity -> ActivityWatcher()
                is Fragment -> FragmentWatcher()
                else -> null
            } as ObjectWatcher<Any>?
            watcher?.watch(obj, cb)
            return watcher
        }

    }

}