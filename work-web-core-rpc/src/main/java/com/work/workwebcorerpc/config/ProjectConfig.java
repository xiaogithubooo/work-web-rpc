package com.work.workwebcorerpc.config;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

@Data
public class ProjectConfig {

    /**
     * 名称
     */
    @Alias("project.name")
    private String name;

    /**
     * 版本号
     */
    @Alias("project.version")
    private String version;

    /**
     * 服务器主机名
     */
    @Alias("project.server.host")
    private String serverHost;

    /**
     * 服务器端口号
     */
    @Alias("project.server.port")
    private Integer serverPort;

}
