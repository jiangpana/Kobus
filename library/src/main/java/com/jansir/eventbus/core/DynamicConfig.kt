package com.jansir.eventbus.core

interface DynamicConfig {
    fun get(key: Key, default: Boolean): Boolean

    enum class Key(val default: Boolean) {
        ENABLE_EVENT_BUS(false),
    }
}

class DefaultDynamicConfig : DynamicConfig {
    override fun get(key: DynamicConfig.Key, default: Boolean): Boolean {
        return default
    }

}