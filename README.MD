# okhttp-ktx  
**Collection of Kotlin extensions for [OkHttp](https://square.github.io/okhttp/)**
###### Obtain this library via [Github](https://github.com/HyperCDN/okhttp-ktx/packages/) or [Jitpack](https://jitpack.io/#HyperCDN/okhttp-ktx) using Maven or Gradle

### Features  
- enhanced proxy support


### Usage Examples  

Okhttp client using a socks proxy with credentials
```kotlin
val withCredentials = socksProxy.extended(UsernamePasswordCredentials(username, password))
val proxiedOkhttpClient = okhttpClient.newProxy(withCredentials)
```
Proxy provider and call manager
```kotlin
val rotatingProvider = RotatingProxyProvider(proxies)
val nextProxy = rotatingProvider.next()

val callManager = SimpleProxyCallManager(okhttpClient, rotatingProvider)
callManager.newCall(request).execute()
```