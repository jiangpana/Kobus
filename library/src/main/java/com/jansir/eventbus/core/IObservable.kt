package com.jansir.eventbus.core

import kotlinx.coroutines.CoroutineScope


interface IObservable {

    fun with(obj: Any): IObservable {
        return this
    }

    fun handle(config: DynamicConfig) = true

    fun <T : Any> post(event: T)

    fun <T : Any> postSticky(event: T) {
        post(event)
    }

    fun <T : Any> observeEvent(
        eventClazz: Class<T>,
        sticky: Boolean = false,
        threadMode: ThreadMode = ThreadMode.MAIN,
        block: suspend CoroutineScope.(T) -> Unit
    ): IObservable {
        return this
    }

    fun clear(obj: Any?)
}