package com.testapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAutoConfiguration
@Configuration
//@EnableCaching
@ComponentScan
@EnableScheduling
public class App {
	public static void main(String[] args) {
//		ApplicationContext	ctx = SpringApplication.run(App.class, args);
		SpringApplication.run(App.class, args);
	}
}
