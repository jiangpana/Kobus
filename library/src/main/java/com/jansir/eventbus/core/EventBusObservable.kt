package com.jansir.eventbus.core

/*
import org.greenrobot.eventbus.EventBus

class EventBusObservable : IObservable,StickyEventAction {

    override fun handle(config: DynamicConfig): Boolean {
        return config.get(
            DynamicConfig.Key.ENABLE_EVENT_BUS,
            DynamicConfig.Key.ENABLE_EVENT_BUS.default
        )
    }

    override fun <T : Any> post(event: T) {
        EventBus.getDefault().post(event)
    }

    override fun <T : Any> postSticky(event: T) {
        EventBus.getDefault().postSticky(event)
    }


    override fun clear(obj: Any?) {
        if (EventBus.getDefault().isRegistered(obj)) {
            EventBus.getDefault().unregister(obj)
        }
    }

    override fun <T : Any> getStickyEvent(clazz: Class<T>): T? {
        return null
    }

    override fun <T : Any> removeStickyEvent(clazz: Class<T>) {
        EventBus.getDefault().removeStickyEvent(clazz)
    }
}*/
