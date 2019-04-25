package com.tang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication

@EnableScheduling
@EnableSwagger2
//@MapperScan("com.tang.dao")

//这个注解用来禁用spring security 便于测试接口
//@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class Springboot2Application {

	public static void main(String[] args) {
		SpringApplication.run(Springboot2Application.class, args);
	}


}
