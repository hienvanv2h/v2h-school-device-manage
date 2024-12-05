package com.vanhuuhien99.school_device_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SchoolDeviceManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolDeviceManagementApplication.class, args);
	}

}
