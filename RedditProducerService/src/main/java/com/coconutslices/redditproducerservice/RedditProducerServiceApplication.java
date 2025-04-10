package com.coconutslices.redditproducerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RedditProducerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditProducerServiceApplication.class, args);
	}

}
