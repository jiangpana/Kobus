package com.jansir.eventbus.core

class ObservableFactory {
    companion object {
        fun build(config: DynamicConfig, sticky: StickyEventAction): IObservable {
//            return if (config.get(
//                    DynamicConfig.Key.ENABLE_EVENT_BUS,
//                    DynamicConfig.Key.ENABLE_EVENT_BUS.default
//                )
//            ) EventBusObservable() else FlowObservable(sticky)
            //only flow is supported for now
            return FlowObservable(sticky)
        }
    }
}