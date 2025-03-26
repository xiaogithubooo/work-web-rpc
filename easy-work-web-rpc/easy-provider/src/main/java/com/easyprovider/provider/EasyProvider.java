package com.easyprovider.provider;

import com.work.easycommon.service.UserService;
import com.work.easyrpc.registry.LocalRegistry;
import com.easyprovider.service.HttpServer;
import com.easyprovider.service.VertxHttpServer;

/**
 * 简易服务提供者
 */
public class EasyProvider {

    /**
     * 注册服务并且启动服务
     */
    public static void main(String[] args) {

        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);

    }

}
