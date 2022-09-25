package com.centime.assignment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.Duration;

@SpringBootApplication
@EnableSwagger2
public class ServiceOneApplication {

	@Value("${resttemplate.connection.timeout}")
	private long connectionTimeOut;

	@Value("${resttemplate.read.timeout}")
	private long readTimeOut;

	@Bean
	public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
		return builder
				.setConnectTimeout(Duration.ofMillis(connectionTimeOut))
				.setReadTimeout(Duration.ofMillis(readTimeOut))
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(ServiceOneApplication.class, args);
	}

}
