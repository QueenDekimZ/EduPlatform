package com.lu.tech.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author QueenDekimZ
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.lu.tech"}) //扫描所有module下的（common/service_base中的com/lu/tech里Swagger）里的config
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }

}
