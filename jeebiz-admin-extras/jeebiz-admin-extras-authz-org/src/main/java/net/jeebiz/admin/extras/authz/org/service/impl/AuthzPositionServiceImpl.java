/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.authz.org.dao.IAuthzPositionDao;
import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzPositionModel;
import net.jeebiz.admin.extras.authz.org.service.IAuthzPositionService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class AuthzPositionServiceImpl extends BaseServiceImpl<AuthzPositionModel, IAuthzPositionDao> implements IAuthzPositionService{

}
