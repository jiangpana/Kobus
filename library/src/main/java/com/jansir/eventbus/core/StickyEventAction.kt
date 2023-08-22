package com.jansir.eventbus.core

interface StickyEventAction{
    fun <T:Any> getStickyEvent(clazz:Class<T>):T?
    fun <T:Any> removeStickyEvent(clazz:Class<T>)
}