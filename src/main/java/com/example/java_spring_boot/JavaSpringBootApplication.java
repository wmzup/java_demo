package com.example.java_spring_boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.example.java_spring_boot.mybatis.mapper")
public class JavaSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaSpringBootApplication.class, args);
	}

}
