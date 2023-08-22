package com.jansir.eventbus.core

import java.util.concurrent.ConcurrentHashMap

class StickyEventManager : StickyEventAction {

    private val TAG = javaClass.simpleName

    private val nameMapStickyEvents = ConcurrentHashMap<String, Any>()

    fun <T : Any> addStickyEvent(event: T) {
        val key = getKey(event.javaClass)
        nameMapStickyEvents[key] = event
    }

    override fun <T : Any> getStickyEvent(clazz: Class<T>): T? {
        val key = getKey(clazz)
        return nameMapStickyEvents[key] as T?
    }

    override fun <T : Any> removeStickyEvent(clazz: Class<T>) {
        val key = getKey(clazz)
        nameMapStickyEvents.remove(key)
    }

    private fun getKey(clazz: Class<*>): String {
        val sName=clazz.simpleName
        return if (sName== Integer::class.java.simpleName || sName == Int::class.java.simpleName) {
            DEFAULT_INT_KEY
        } else {
            clazz.simpleName.lowercase()
        }
    }

    companion object {
        private const val DEFAULT_INT_KEY = "int"
    }

}