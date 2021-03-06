/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sessions.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionsConfiguration {
	
	@Bean
	public FlywayFluentConfiguration flywaySessionsConfiguration() {
		
		FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("sessions",
				"会话管理-模块初始化（会话列表、监控、强制退出等）", "1.0.0");
		
		return configuration;
	}

}
