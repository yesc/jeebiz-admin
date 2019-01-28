package net.jeebiz.admin.extras.basedata.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasedataConfiguration {
	
	@Bean
	public FlywayFluentConfiguration flywayBasedataConfiguration() {
		
		FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("basedata",
				"基础数据-模块初始化（各种基础设置的初始化）", "1.0.0");
		
		return configuration;
	}

}
