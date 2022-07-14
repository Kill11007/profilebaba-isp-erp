package com.knackitsolutions.profilebaba.isperp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class IspERPApplication {

	public static void main(String[] args) {
		SpringApplication.run(IspERPApplication.class, args);
	}

}
