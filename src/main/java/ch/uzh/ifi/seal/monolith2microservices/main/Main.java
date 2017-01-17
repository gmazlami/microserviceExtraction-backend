package ch.uzh.ifi.seal.monolith2microservices.main;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"ch.uzh.ifi.seal.monolith2microservices.controllers","ch.uzh.ifi.seal.monolith2microservices.main", "ch.uzh.ifi.seal.monolith2microservices.services",  "ch.uzh.ifi.seal.monolith2microservices.git"})
@EnableJpaRepositories(basePackages = "ch.uzh.ifi.seal.monolith2microservices.persistence")
@EntityScan(basePackages = {"ch.uzh.ifi.seal.monolith2microservices.models"})
@EnableAsync
public class Main extends AsyncConfigurerSupport{

	public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
	}
	
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Monolith2Microservice-");
        executor.initialize();
        return executor;
    }

}
