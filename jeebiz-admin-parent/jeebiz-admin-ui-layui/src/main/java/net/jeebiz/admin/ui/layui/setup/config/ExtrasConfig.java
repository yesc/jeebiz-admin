package net.jeebiz.admin.ui.layui.setup.config;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import net.jeebiz.admin.ui.layui.setup.handler.LayuiATreeFeatureHandler;
import net.jeebiz.admin.ui.layui.setup.handler.LayuiAuthTreeFeatureHandler;
import net.jeebiz.admin.extras.authz.feature.setup.handler.FeatureDataHandlerFactory;

@Configuration
public class ExtrasConfig {

	@PostConstruct
	public void initExtras() {
		FeatureDataHandlerFactory.newHandler("atree", new LayuiATreeFeatureHandler());
		FeatureDataHandlerFactory.newHandler("authtree", new LayuiAuthTreeFeatureHandler());
	}
	
}
