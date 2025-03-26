package com.work.easyrpc.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册中心
 */
public class LocalRegistry {

    /**
     * 注册存储(每一条记录都是 服务名, 类路径)
     */
    private static final Map<String, Class<?>> map = new ConcurrentHashMap<>();

    /**
     * 注册服务
     */
    public static void register(String serviceName, Class<?> implClass) {
        map.put(serviceName, implClass);
    }

    /**
     * 注销服务
     */
    public static void remove(String serviceName) {
        map.remove(serviceName);
    }

    /**
     * 获取服务
     */
    public static Class<?> get(String serviceName) {
        return map.get(serviceName);
    }

}
