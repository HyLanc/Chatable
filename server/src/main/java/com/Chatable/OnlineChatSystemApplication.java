package com.Chatable;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动类
 */
@SpringBootApplication
@MapperScan("com.Chatable.dao")
public class OnlineChatSystemApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(OnlineChatSystemApplication.class, args);
    }
}
