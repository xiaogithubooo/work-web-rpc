package com.work.easyrpc.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.work.easyrpc.request.RpcRequest;
import com.work.easyrpc.response.RpcResponse;
import com.work.easyrpc.serializer.JdkSerializer;
import com.work.easyrpc.serializer.Serializer;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 服务代理
 */
public class ServiceProxy implements InvocationHandler { // InvocationHandler 允许在方法执行前后进行拦截

    /**
     * 调用代理(由 "JDK 动态代理机制" 在代理对象调用接口方法时自动触发)
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 指定序列化器
        Serializer serializer = new JdkSerializer();

        // 构造 RPC 请求
        RpcRequest rpcRequest = RpcRequest
            .builder()
            .serviceName(method.getDeclaringClass().getName()) // 设置服务名
            .methodName(method.getName()) // 设置方法名
            .parameterTypes(method.getParameterTypes()) // 设置参数类型
            .args(args) // 设置参数
            .build()
        ;

        // 发送携带 RPC 请求的 HTTP 请求
        try {
            byte[] bodyBytes = serializer.serialize(rpcRequest); // 序列化 RPC 请求
            try (HttpResponse httpResponse = HttpRequest // 发送请求
                .post("http://localhost:8080") // TODO: 这里地址被硬编码了, 需要使用注册中心和服务发现机制解决
                .body(bodyBytes)
                .execute()
            ) {
                // 接受携带 RPC 响应的 HTTP 响应
                byte[] result = httpResponse.bodyBytes();
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class); // 反序列化 RPC 响应
                return rpcResponse.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
