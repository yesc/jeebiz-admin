/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.setup.event;

import java.util.List;

import org.springframework.biz.context.event.EnhancedEvent;

import com.google.common.collect.Lists;

import net.jeebiz.admin.extras.inform.dao.entities.InformModel;

/**
 */
@SuppressWarnings("serial")
public class InformCreatedEvent extends EnhancedEvent<List<InformModel>> {
	
	public InformCreatedEvent(Object source, InformModel inform) {
		super(source, Lists.newArrayList(inform));
	}
	
	public InformCreatedEvent(Object source, List<InformModel> informs) {
		super(source, informs);
	}
	
}
