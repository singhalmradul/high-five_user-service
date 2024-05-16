package io.github.singhalmradul.userservice;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableCaching
@EnableFeignClients
public class UserServiceApplication {

	public static void main(String[] args) {

		run(UserServiceApplication.class, args);
	}

}
