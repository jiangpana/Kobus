package com.jansir.eventbus

import android.app.Fragment
import android.content.Context
import androidx.lifecycle.ViewModel
import com.jansir.eventbus.core.*
import com.jansir.eventbus.lifecycle.ObjectWatcher
import com.jansir.eventbus.lifecycle.WatcherFactory
import kotlinx.coroutines.CoroutineScope
import java.util.*

object Kobus {

    private val TAG = javaClass.simpleName

    private val mObservables = WeakHashMap<Any, IObservable>()

    private val mObjectWatches = hashMapOf<Any, ObjectWatcher<*>?>()

    private val mLock = Any()

    private val mStickyEventManager = StickyEventManager()

    private var mConfig: DynamicConfig = DefaultDynamicConfig()


    fun <T : Any> post(
        event: T
    ) {
        mObservables.values.forEach {
            it.post(event)
        }
    }

    fun <T : Any> postSticky(event: T) {
        mStickyEventManager.addStickyEvent(event)
        mObservables.values.forEach {
            it.postSticky(event)
        }
    }

    fun <T : Any> removeStickyEvent(event: T) {
        mStickyEventManager.removeStickyEvent(event.javaClass)
    }

    internal fun register(obj: Fragment): IObservable {
        return with(obj as Any)
    }

    internal fun register(obj: androidx.fragment.app.Fragment): IObservable {
        return with(obj as Any)
    }

    internal fun register(obj: Context): IObservable {
        return with(obj as Any)
    }

    internal fun register(obj: ViewModel): IObservable {
        return with(obj as Any)
    }

    internal fun register(obj: EventScope): IObservable {
        return with(obj as Any)
    }


    fun isRegistered(obj: Any): Boolean {
        return getObservable(obj) != null
    }

    internal fun getObservable(key: Any): IObservable? {
        synchronized(mLock) {
            return mObservables[key]
        }
    }

    private fun with(obj: Any): IObservable {
        synchronized(mLock) {
            mObservables[obj] =
                mObservables[obj] ?: newObservable(mConfig, mStickyEventManager)
            if (mObjectWatches[obj] == null) {
                newObjectWatcher(obj, object : ObjectWatcher.ObjectState {
                    override fun onDestroy() {
                        unregister(obj)
                    }
                })?.also {
                    mObjectWatches[obj] = it
                }
            }
        }
        return getObservable(obj)!!.with(obj)
    }


    private fun newObservable(
        config: DynamicConfig,
        stickyEventAction: StickyEventAction
    ): IObservable {
        return ObservableFactory.build(config, stickyEventAction)
    }

    private fun newObjectWatcher(obj: Any, cb: ObjectWatcher.ObjectState): ObjectWatcher<*>? {
        return WatcherFactory.build(obj, cb)
    }

    internal fun unregister(obj: CoroutineScope) {
        unregister(obj as Any)
    }

    fun unregister(obj: Any) {
        if (!isRegistered(obj)) return
        synchronized(mLock) {
            mObservables.remove(obj)?.also {
                it.clear(obj)
            }
            mObjectWatches.remove(obj)
        }
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("$TAG info :\n")
        stringBuilder.append("observables size = ${mObservables.size}\n")
        mObservables.forEach {
            stringBuilder.append("${it.key} , ${it.value.toString()} \n")
        }
        stringBuilder.append("objectWatches size = ${mObservables.size}\n")
        mObjectWatches.forEach {
            stringBuilder.append("${it.key} , ${it.value.toString()} \n")
        }
        stringBuilder.append("\n")
        return stringBuilder.toString()
    }
}