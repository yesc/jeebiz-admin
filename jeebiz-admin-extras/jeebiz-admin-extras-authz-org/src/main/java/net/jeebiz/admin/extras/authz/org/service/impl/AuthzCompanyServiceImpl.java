/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.authz.org.dao.IAuthzCompanyDao;
import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzCompanyModel;
import net.jeebiz.admin.extras.authz.org.service.IAuthzCompanyService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class AuthzCompanyServiceImpl extends BaseServiceImpl<AuthzCompanyModel, IAuthzCompanyDao> implements IAuthzCompanyService{

}
