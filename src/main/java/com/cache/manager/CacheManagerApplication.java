package com.cache.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.hazelcast.repository.config.EnableHazelcastRepositories;


@SpringBootApplication
@EnableHazelcastRepositories
public class CacheManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CacheManagerApplication.class, args);
	}

}
