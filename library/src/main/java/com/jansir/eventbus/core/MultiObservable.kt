package com.jansir.eventbus.core

import kotlinx.coroutines.CoroutineScope

class MultiObservable(
    private val observables: List<IObservable>,
    private val config: DynamicConfig
) : IObservable {


    override fun with(obj: Any): IObservable {
        observables.forEach {
            if (it.handle(config)) {
                it.with(obj)
            }
        }
        return this
    }

    override fun <T : Any> post(event: T) {
        observables.forEach {
            if (it.handle(config)) {
                it.post(event)
            }
        }
    }

    override fun <T : Any> observeEvent(
        eventClazz: Class<T>,
        sticky: Boolean,
        threadMode: ThreadMode,
        block: suspend CoroutineScope.(T) -> Unit
    ): IObservable {
        observables.forEach {
            if (it.handle(config)) {
                it.observeEvent(eventClazz, sticky, threadMode, block)
            }
        }
        return this
    }

    override fun clear(obj: Any?) {
        observables.forEach {
            it.clear(obj)
        }
    }
}