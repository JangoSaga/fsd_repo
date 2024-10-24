package com.conference.participants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.conference.participants",
		"com.conference.participants.controller",
		"com.conference.participants.entity",
		"com.conference.participants.repository"
})
@EntityScan(basePackages = {
		"com.conference.participants.entity"
})
@EnableJpaRepositories(basePackages = {
		"com.conference.participants.repository"
})
public class ParticipantsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParticipantsApplication.class, args);
	}
}