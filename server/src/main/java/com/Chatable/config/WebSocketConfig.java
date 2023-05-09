package com.Chatable.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author 韦润泽, 李君哲, 何雨宸, 王开
 * @create 2023-04-30 23:06
 */

/**
 * WebSocket配置类
 */
@Configuration
public class WebSocketConfig {

    /**
     * 注入ServerEndpointExporter的bean对象，自动注册使用了@ServerEndpoint注解的bean
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
