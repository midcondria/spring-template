package com.commerce.team;

import com.commerce.team.global.config.AppConfig;
import com.commerce.team.global.config.AwsConfig;
import com.commerce.team.global.config.IamportConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AppConfig.class, IamportConfig.class, AwsConfig.class})
public class TeamApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamApplication.class, args);
	}
}
