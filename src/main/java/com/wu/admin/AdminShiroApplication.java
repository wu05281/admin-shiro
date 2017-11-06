package com.wu.admin;

import com.wu.admin.utils.SnowflakeIdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AdminShiroApplication {

	@Bean
	public SnowflakeIdWorker snowflakeIdWorker(){
		return new SnowflakeIdWorker(0,0);
	}

	public static void main(String[] args) {
		SpringApplication.run(AdminShiroApplication.class, args);
	}
}
