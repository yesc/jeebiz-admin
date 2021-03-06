/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.service;

import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzOrganizationModel;
import net.jeebiz.boot.api.service.BaseService;

public interface IAuthzOrganizationService extends BaseService<AuthzOrganizationModel> {
	
	public int getRootCount();

	public int getDeptCount(String id);
	
}
