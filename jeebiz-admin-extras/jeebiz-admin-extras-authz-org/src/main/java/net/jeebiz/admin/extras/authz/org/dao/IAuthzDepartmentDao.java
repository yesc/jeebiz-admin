package net.jeebiz.admin.extras.authz.org.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzDepartmentModel;
import net.jeebiz.boot.api.dao.BaseDao;

@Mapper
public interface IAuthzDepartmentDao extends BaseDao<AuthzDepartmentModel> {

	
}
