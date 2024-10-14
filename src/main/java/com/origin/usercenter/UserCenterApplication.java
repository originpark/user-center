package com.origin.usercenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.origin.usercenter.mapper")
public class UserCenterApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext application = SpringApplication.run(UserCenterApplication.class, args);
	}

}
