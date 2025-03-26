package com.work.easyrpc.proxy;

import java.lang.reflect.Proxy;

/**
 * 服务代理工厂
 */
public class ServiceProxyFactory {

    /**
     * 根据服务类获取代理对象
     */
    public static <T> T getProxy(Class<T> serviceClass) { // 可以是无需声明的接口, Java 的 Class<T> 不仅可以表示具体的类, 也可以表示接口
        // 利用 JDK 动态代理创建一个代理对象
        return (T) Proxy.newProxyInstance(
            serviceClass.getClassLoader(), // 用于加载新生成的代理类
            new Class[]{serviceClass}, // 代理对象会实现 serviceClass 这个接口
            new ServiceProxy() // 创建代理对象的处理器
        );
    }

}
