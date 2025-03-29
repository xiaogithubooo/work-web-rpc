package com.work.workwebcorerpc;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.bean.BeanUtil;
import com.work.workwebcorerpc.config.ProjectConfig;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        // 创建 ProjectConfig 对象
        ProjectConfig projectConfig = new ProjectConfig();

        // 加载配置文件
        Properties properties = new Properties();
        try (InputStream inputStream = FileUtil.getInputStream("config.properties")) {
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 将 Properties 中的值映射到 ProjectConfig 对象
        BeanUtil.copyProperties(properties, projectConfig);

        // 输出加载的配置值
        System.out.println("Project Name: " + projectConfig.getName());
        System.out.println("Project Version: " + projectConfig.getVersion());
        System.out.println("Server Host: " + projectConfig.getServerHost());
        System.out.println("Server Port: " + projectConfig.getServerPort());
    }
}
