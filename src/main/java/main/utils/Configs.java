package main.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@EnableAutoConfiguration
@Configuration
public class Configs {
	
	@Value("${git.localrepo}")
	public String localRepositoryDirectory;
	

}
