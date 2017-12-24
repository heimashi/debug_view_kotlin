# debug_view_kotlin
debug-view是用kotlin实现的用于Android调试的**浮层调试控制台**这个控制台会一直浮在app的UI最上层，
用于实时的直观的显示一些app的信息，例如app使用的内存信息、app的实时帧率FPS、文字log信息。


How to use
----------
- 对于已经支持kotlin的项目,在Application的module里添加依赖即可
```groovy
   dependencies {
     debugImplementation 'com.sw.debug.view:debug-view:1.0.0'
     releaseImplementation 'com.sw.debug.view:debug-view-no-op:1.0.0'
     testImplementation 'com.sw.debug.view:debug-view-no-op:1.0.0'
   }
```
如果是java项目需要先添加kotlin的支持，步骤是在Android Studio中选择tools ---> kotlin ---> Configure Kotlin in Project

- 初始化，建议在应用的Application中，初始化后debug-view就会生成一个浮层View显示内存和FPS
```kotlin
    private fun initDebugView() {
        DebugViewWrapper.instance.init(
                DebugViewWrapper.Builder(this)
                        .viewWidth(250) /* the width of debug-view */
                        .bgColor(0x6f677700) /* the color of debug-view */
                        .alwaysShowOverlaySetting(true) /* the flag for always showing Overlay Setting */
                        .logMaxLines(20) /* the max lines of log */
        )


        DebugViewWrapper.instance.show()
    }
```

- 自定义输出文字log到浮层控制台中
```kotlin
    LogModule.instance.log("log...log")
```




