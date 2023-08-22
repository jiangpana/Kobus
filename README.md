# Kobus
一个支持Kotlin 协程的事件总线框架 ，使用Flow实现   
An event bus framework that supports coroutines, implemented using Flow 

##

## 为什么要造这个轮子？

业界有那么多的事件总线框架，比较著名的有EventBus，为什么还要造这个轮子？事出必有因，大致有以下两点：

1.  EventBus不支持Kotlin 协程

EventBus 不支持订阅协程挂起方法，要在普通订阅方法中能使用协程调用协程专属的挂起方法，必须要自己维护一个协程域，相当麻烦。

2.  EventBus很容易出现滥用的情况，导致项目难维护

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/747c77cbaf634dd99bf4d289e6840ec4~tplv-k3u1fbpfcp-zoom-1.image)

基于EventBus 的api设计，我们可以看到EventBus可以在任何类中进行订阅，在一些类中完全可以不需要借助EventBus就可以完成通信，但还是有很多人使用EventBus来进行通信，如果合理使用还好，不合理使用就很容易造成内存泄漏和项目难维护的现象。

但是要造这个轮子主要还是因为第一点原因，EventBus不支持协程。

## 有什么优势？

良好的架构设计，目前基于Kotlin 协程 Flow 实现，以后 Flow 过时或者废弃也可以快速使用其他替代品来替代。

优雅监控生命周期，在生命周期结束时自动解注册，防止内存泄漏。目前支持的有以下：

Fragment , Activity , ViewModel , LifecycleOwner 。

如果Fragment , Activity , ViewModel , LifecycleOwner 继承自协程域，或者像ViewModel提供了协程域扩展函数则使用其协程域，不提供协程域则使用内部维护的协程域。

api 设计通俗易用，支持链式调用。

无任何反射。

## 如何使用？

在 Fragment , Activity , ViewModel , LifecycleOwner 中直接订阅即可，框架内部会在生命周期onDestroy时自动解注册以及取消协程。

```kotlin
class MainActivity : ComponentActivity() {
    val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeEvent(String::class.java) {
            launch {
                Log.d(TAG, "observeEvent: 收到$it")
            }
            withContext(Dispatchers.IO) {

            }
            async {

            }
        }.observeEvent(Int::class.java) {
            Log.d(TAG, "observeEvent: 收到$it")
        }.observeEvent(Long::class.java) {
            Log.d(TAG, "observeEvent: 收到$it")
        }.observeEvent(TestEvent::class.java) {
            Log.d(TAG, "observeEvent: 收到${it.message}")
        }.observeEvent(TestEvent::class.java) {
            //会抛异常，一个订阅者不可多次注册同一事件类型
            Log.d(TAG, "observeEvent: 收到${it.message}")
        }
    }
}
```

在其他类中使用需实现EventScope ，EventScope继承自协程域，使用完之后需要手动调用unregister 解注册以及取消协程，如下。

```kotlin
class TestClass : EventScope by MainEventScope() {
    val TAG = javaClass.simpleName

    init {
        observeEvent(String::class.java) {
            Log.d(TAG, "observeEvent: 收到$it")
        }.observeEvent(Int::class.java) {
            Log.d(TAG, "observeEvent: 收到$it")
        }.observeEvent(Long::class.java) {
            Log.d(TAG, "observeEvent: 收到$it")
        }
    }

    fun clear() {
        unregister()
    }
}
```

发送事件，支持粘性事件

```kotlin
        Kobus.post("post str")
        Kobus.postSticky("post sticky str")
```

## Kobus框架命名由来

kotlin event bus 秉着读起来顺口的原则从中取了五个字母。

## 维护

长久维护，使用上有什么问题 或者想要加入什么功能，直接提Issue便可。也欢迎贡献代码一起维护。

## License

随便复制，随便修改 ( Fork as you like, modify as you like )
