package com.hqs.rediscache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

//@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX,pattern="com.hqs.rediscache.datainit.*")})
@SpringBootApplication
public class RedisCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisCacheApplication.class, args);
	}

}
