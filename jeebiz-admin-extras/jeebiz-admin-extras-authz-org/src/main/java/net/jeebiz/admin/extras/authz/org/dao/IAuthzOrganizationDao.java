package net.jeebiz.admin.extras.authz.org.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzCompanyModel;
import net.jeebiz.boot.api.dao.BaseDao;

@Mapper
public interface IAuthzCompanyDao extends BaseDao<AuthzCompanyModel> {

}
