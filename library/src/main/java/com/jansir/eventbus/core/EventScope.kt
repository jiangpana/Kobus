package com.jansir.eventbus.core

import android.app.Fragment
import android.content.Context
import androidx.lifecycle.ViewModel
import com.jansir.eventbus.Kobus
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

interface EventScope : CoroutineScope

fun MainEventScope(): EventScope = EventScopeImpl()

fun EventScope.unregister() {
    Kobus.unregister(this)
}

internal class EventScopeImpl(override val coroutineContext: CoroutineContext = (SupervisorJob() + Dispatchers.Main)) :
    EventScope

fun <T : Any> EventScope.observeEvent(
    eventClazz: Class<T>,
    sticky: Boolean = false,
    threadMode: ThreadMode = ThreadMode.MAIN,
    block: suspend CoroutineScope.(T) -> Unit
): IObservable {
    if (!Kobus.isRegistered(this)) {
        Kobus.register(this)
    }
    return Kobus.getObservable(this)!!.observeEvent(eventClazz, sticky, threadMode, block)
}

@Deprecated("android.app.Fragment is Deprecated")
fun <T : Any> Fragment.observeEvent(
    eventClazz: Class<T>,
    sticky: Boolean = false,
    threadMode: ThreadMode = ThreadMode.MAIN,
    block: suspend CoroutineScope.(T) -> Unit
): IObservable {
    if (!Kobus.isRegistered(this)) {
        Kobus.register(this)
    }
    return Kobus.getObservable(this)!!.observeEvent(eventClazz, sticky, threadMode, block)
}

fun <T : Any> androidx.fragment.app.Fragment.observeEvent(
    eventClazz: Class<T>,
    sticky: Boolean = false,
    threadMode: ThreadMode = ThreadMode.MAIN,
    block: suspend CoroutineScope.(T) -> Unit
): IObservable {
    if (!Kobus.isRegistered(this)) {
        Kobus.register(this)
    }
    return Kobus.getObservable(this)!!.observeEvent(eventClazz, sticky, threadMode, block)
}

fun <T : Any> Context.observeEvent(
    eventClazz: Class<T>,
    sticky: Boolean = false,
    threadMode: ThreadMode = ThreadMode.MAIN,
    block: suspend CoroutineScope.(T) -> Unit
): IObservable {
    if (!Kobus.isRegistered(this)) {
        Kobus.register(this)
    }
    return Kobus.getObservable(this)!!.observeEvent(eventClazz, sticky, threadMode, block)
}

fun <T : Any> ViewModel.observeEvent(
    eventClazz: Class<T>,
    sticky: Boolean = false,
    threadMode: ThreadMode = ThreadMode.MAIN,
    block: suspend CoroutineScope.(T) -> Unit
): IObservable {
    if (!Kobus.isRegistered(this)) {
        Kobus.register(this)
    }
    return Kobus.getObservable(this)!!.observeEvent(eventClazz, sticky, threadMode, block)
}


