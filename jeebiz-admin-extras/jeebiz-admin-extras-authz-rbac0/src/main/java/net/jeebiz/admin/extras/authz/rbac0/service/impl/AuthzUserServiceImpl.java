/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.rbac0.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;

import net.jeebiz.admin.extras.authz.feature.dao.IAuthzFeatureDao;
import net.jeebiz.admin.extras.authz.rbac0.dao.IAuthzRoleDao;
import net.jeebiz.admin.extras.authz.rbac0.dao.IAuthzUserDao;
import net.jeebiz.admin.extras.authz.rbac0.dao.entities.AuthzRoleModel;
import net.jeebiz.admin.extras.authz.rbac0.dao.entities.AuthzUserAllotRoleModel;
import net.jeebiz.admin.extras.authz.rbac0.dao.entities.AuthzUserDetailModel;
import net.jeebiz.admin.extras.authz.rbac0.dao.entities.AuthzUserModel;
import net.jeebiz.admin.extras.authz.rbac0.service.IAuthzUserService;
import net.jeebiz.boot.api.dao.entities.PaginationModel;
import net.jeebiz.boot.api.service.BaseServiceImpl;
import net.jeebiz.boot.api.utils.CollectionUtils;

@Service
public class AuthzUserServiceImpl extends BaseServiceImpl<AuthzUserDetailModel, IAuthzUserDao> implements IAuthzUserService {

	@Autowired
	private IAuthzFeatureDao authzFeatureDao;
	@Autowired
	private IAuthzRoleDao authzRoleDao;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int setStatus(String userId, String status) {
		return getDao().setStatus(userId, status);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insert(AuthzUserDetailModel model) {
		model.setUsername(StringUtils.defaultString(model.getUsername(), model.getPhone()));
		int ct = getDao().insert(model);
		getDao().insertDetail(model);
		getAuthzRoleDao().setUsers(model.getRoleId(), Lists.newArrayList(model.getId()));
		return ct;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int batchDelete(List<?> list) {
		if(CollectionUtils.isEmpty(list)) {
			return 0;
		}
		getDao().batchDeleteDetail(list);
		getDao().batchDeleteRole(list);
		return getDao().batchDelete(list);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int delete(String id) {
		getDao().deleteDetail(id);
		getDao().deleteRole(id);
		return getDao().delete(id);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int update(AuthzUserDetailModel model) {
		int ct = getDao().update(model);
		getDao().updateDetail(model);
		getDao().updateRole(model);
		return ct;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updatePwd(List<String> users, String password) {
		int ct = 0;
		for (String userid : users) {
			ct += getDao().updatePwd(userid, password);
		}
		return ct;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int resetPwd(String userId, String oldPassword, String password) {
		return getDao().resetPwd(userId, oldPassword, password);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int doAllot(AuthzUserAllotRoleModel model) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int doUnAllot(AuthzUserAllotRoleModel model) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getCountByName(String username) {
		return getDao().getCountByName(username);
	}
	
	@Override
	public List<String> getRoles(String userId) {
		return getDao().getRoles(userId);
	}
	
	@Override
	public List<String> getPermissions(String userId) {
		return getDao().getPermissions(userId);
	}
	
	@Override
	public Page<AuthzRoleModel> getPagedAllocatedList(AuthzUserModel model) {
		
		PaginationModel tModel = (PaginationModel) model;
		
		Page<AuthzRoleModel> page = new Page<AuthzRoleModel>(tModel.getPageNo(), tModel.getLimit());
		if("asc".equalsIgnoreCase(tModel.getSortOrder())) {
			page.setAsc(tModel.getSortName());
		} else {
			page.setDesc(tModel.getSortName());
		}
		
		List<AuthzRoleModel> records = getDao().getPagedAllocatedList(page, model);
		page.setRecords(records);
		
		return page;
		
	}

	@Override
	public Page<AuthzRoleModel> getPagedUnAllocatedList(AuthzUserModel model) {
		PaginationModel tModel = (PaginationModel) model;
		
		Page<AuthzRoleModel> page = new Page<AuthzRoleModel>(tModel.getPageNo(), tModel.getLimit());
		if("asc".equalsIgnoreCase(tModel.getSortOrder())) {
			page.setAsc(tModel.getSortName());
		} else {
			page.setDesc(tModel.getSortName());
		}
		
		List<AuthzRoleModel> records = getDao().getPagedUnAllocatedList(page, model);
		page.setRecords(records);
		
		return page;
	}

	public IAuthzFeatureDao getAuthzFeatureDao() {
		return authzFeatureDao;
	}

	public void setAuthzFeatureDao(IAuthzFeatureDao authzFeatureDao) {
		this.authzFeatureDao = authzFeatureDao;
	}

	public IAuthzRoleDao getAuthzRoleDao() {
		return authzRoleDao;
	}

	public void setAuthzRoleDao(IAuthzRoleDao authzRoleDao) {
		this.authzRoleDao = authzRoleDao;
	}
	
}
