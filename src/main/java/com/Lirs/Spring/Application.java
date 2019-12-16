package com.Lirs.Spring;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		//app.setBannerMode(Banner.Mode.OFF); 关于Banner（启动图案）
		app.run(args);
	}
}
