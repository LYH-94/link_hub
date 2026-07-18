package com.lyh.linkhub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.lyh.linkhub.mapper")
@SpringBootApplication
public class LinkhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkhubApplication.class, args);
	}

}
