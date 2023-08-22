package com.jansir.eventbus.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jansir.eventbus.ext.getEventKeyFormClazz
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.HashMap

class FlowObservable(private val stickyAction: StickyEventAction) : IObservable {

    private val TAG = javaClass.simpleName

    private val mFlows = HashMap<String, MutableSharedFlow<*>>()

    private val mObserverJobs = Collections.newSetFromMap(WeakHashMap<Job?, Boolean>())

    private var mWeakRefScope: WeakReference<CoroutineScope>? = null

    private var mScope: CoroutineScope? = null

    override fun with(obj: Any): IObservable {
        if (obj is CoroutineScope) {
            mWeakRefScope = WeakReference(obj)
        }
        if (obj is ViewModel) {
            mWeakRefScope = WeakReference(obj.viewModelScope)
        }
        if (mWeakRefScope == null) {
            mScope = MainScope()
            mWeakRefScope = WeakReference(mScope)
        }
        return this
    }

    override fun <T : Any> post(event: T) {
        val key = getEventKeyFormClazz(event.javaClass)
        (mFlows[key] as? MutableSharedFlow<T>)?.apply {
            tryEmit(event)
        }
    }

    override fun <T : Any> observeEvent(
        eventClazz: Class<T>,
        sticky: Boolean,
        threadMode: ThreadMode,
        block: suspend CoroutineScope.(T) -> Unit
    ): IObservable {
        val disCtx = getThreadDispatcher(threadMode)
        mWeakRefScope?.get()?.launch(disCtx) {
            val key = getEventKeyFormClazz(eventClazz)
            mFlows[key] = getFlow(key) ?: newFlow<T>()
            if (sticky) {
                stickyAction.getStickyEvent(eventClazz)?.let {
                    block(it)
                }
            }
            (mFlows[key] as MutableSharedFlow<T>).collect {
                block(it)
            }
        }.also {
            mObserverJobs.add(it)
        }
        return this
    }

    private fun getThreadDispatcher(threadMode: ThreadMode) =
        when (threadMode) {
            ThreadMode.BACKGROUND -> Dispatchers.Default
            ThreadMode.MAIN -> Dispatchers.Main
            ThreadMode.IO -> Dispatchers.IO
        }

    private fun getFlow(key: String): MutableSharedFlow<*>? {
        assert(mFlows[key] == null) {
            "The same event {key = $key} can only be observed once"
        }
        return mFlows[key]
    }

    private fun <T : Any> newFlow(): MutableSharedFlow<*> {
        return MutableSharedFlow<T>(
            extraBufferCapacity = Int.MAX_VALUE
        )
    }

    override fun clear(obj: Any?) {
        mObserverJobs.forEach {
            it?.cancel()
        }
        mWeakRefScope?.get()?.cancel()
        mScope?.cancel()
        mObserverJobs.clear()
        mFlows.clear()
    }


    override fun toString(): String {
        val strBuilder = StringBuilder()
        strBuilder.append("$TAG observerJobs.size = ${mObserverJobs.size} , flows.size=${mFlows.size}")
        return strBuilder.toString()
    }

}