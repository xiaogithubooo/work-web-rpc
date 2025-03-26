package com.work.easyconsumer;

import com.work.easycommon.entity.User;
import com.work.easycommon.service.UserService;
import com.work.easyrpc.proxy.ServiceProxyFactory;

/**
 * 简易服务消费者
 */
public class EasyConsumer {

    public static void main(String[] args) {

        // 设置用户需要传递的参数
        User user = new User();
        user.setName("limou");

        // 获取服务提供者调用
        UserService userService = ServiceProxyFactory.getProxy(UserService.class); // 这里的 UserService.class 可以仅仅是一个接口, 不过模拟场景下我们可以直接获取, 正常来说是只能获取到接口的

        User newUser = userService.getUser(user); // userService 的方法在被调用的时候, 会被 ServiceProxy 处理, 然后封装 PRC 报文, 看起来就是单纯的本地调用

        if (newUser != null) {
            System.out.println(newUser.getName());
        }
        else {
            System.out.println("user == null");
        }
    }

}
