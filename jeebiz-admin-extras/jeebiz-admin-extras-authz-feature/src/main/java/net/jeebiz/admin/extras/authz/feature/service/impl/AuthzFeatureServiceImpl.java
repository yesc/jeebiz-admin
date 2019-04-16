/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.feature.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.jeebiz.boot.api.service.BaseServiceImpl;
import net.jeebiz.admin.extras.authz.feature.dao.IAuthzFeatureDao;
import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.admin.extras.authz.feature.service.IAuthzFeatureService;

@Service
public class AuthzFeatureServiceImpl extends BaseServiceImpl<AuthzFeatureModel, IAuthzFeatureDao> implements IAuthzFeatureService {
 
	@Override
	public List<AuthzFeatureModel> getFeatureList() {
		return getDao().getFeatureList();
	}

}
