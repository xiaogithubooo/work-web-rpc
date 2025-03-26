package com.easyprovider.service;

import com.work.easyrpc.request.RpcRequest;
import com.work.easyrpc.response.RpcResponse;
import com.work.easyrpc.registry.LocalRegistry;
import com.work.easyrpc.serializer.JdkSerializer;
import com.work.easyrpc.serializer.Serializer;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * HTTP 拦截处理器
 */
public class HttpServerHandler implements Handler<HttpServerRequest> {

    /**
     * 处理 HTTP 请求
     */
    @Override
    public void handle(HttpServerRequest request) {

        // 查看获取到的日志
        System.out.println("Received request: " + request.method() + " " + request.uri());

        // 指定序列化器
        final Serializer serializer = new JdkSerializer();

        // 异步处理 HTTP 请求
        request.bodyHandler(body -> {
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try {
                rpcRequest = serializer.deserialize(bytes, RpcRequest.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 构造响应结果对象
            RpcResponse rpcResponse = new RpcResponse();

            // 如果请求为 null，直接返回
            if (rpcRequest == null) {
                rpcResponse.setMessage("rpcRequest is null");
                doResponse(request, rpcResponse, serializer);
                return;
            }

            try {
                // 获取要调用的服务实现类, 通过反射调用
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName()); // 从注册中心中获取需要反射的类
                Method method = implClass.getMethod( // 获取需要调用的方法
                    rpcRequest.getMethodName(), // 填充方法名
                    rpcRequest.getParameterTypes() // 填充参数类型
                );
                Object result = method.invoke( // 调用方法
                    implClass.newInstance(), // 创建实例
                    rpcRequest.getArgs() // 填充参数
                );
                // 封装返回结果
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("ok");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }
            // 响应
            doResponse(request, rpcResponse, serializer);
        });
    }

    /**
     * 处理 HTTP 响应
     */
    void doResponse(HttpServerRequest request, RpcResponse rpcResponse, Serializer serializer) {
        HttpServerResponse httpServerResponse = request
                .response()
                .putHeader("content-type", "application/json");
        try {
            // 序列化
            byte[] serialized = serializer.serialize(rpcResponse);
            httpServerResponse.end(Buffer.buffer(serialized));
        } catch (IOException e) {
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }

}
