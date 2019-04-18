/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.feature.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.jeebiz.boot.api.service.BaseServiceImpl;
import net.jeebiz.admin.extras.authz.feature.dao.IAuthzFeatureOptDao;
import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureOptModel;
import net.jeebiz.admin.extras.authz.feature.service.IAuthzFeatureOptService;

@Service
public class AuthzFeatureOptServiceImpl extends BaseServiceImpl<AuthzFeatureOptModel, IAuthzFeatureOptDao> implements IAuthzFeatureOptService {

	@Override
	public List<AuthzFeatureOptModel> getFeatureOpts() {
		return getDao().getFeatureOpts();
	}
	
	@Override
	public List<AuthzFeatureOptModel> getFeatureOptList(String featureId, boolean visible) {
		return getDao().getFeatureOptList(featureId, visible ? "1" : "0");
	}
	
}