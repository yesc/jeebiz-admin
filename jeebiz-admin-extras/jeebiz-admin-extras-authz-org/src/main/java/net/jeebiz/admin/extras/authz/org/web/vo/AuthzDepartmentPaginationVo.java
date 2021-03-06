/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.web.vo;

import org.hibernate.validator.constraints.SafeHtml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.jeebiz.boot.api.vo.AbstractPaginationVo;

@ApiModel(value = "AuthzDepartmentPaginationVo", description = "部门信息分页查询参数")
public class AuthzDepartmentPaginationVo extends AbstractPaginationVo {

	/**
	 * 部门名称
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "部门名称")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String name;
	/**
	 * 机构ID编号
	 */
	@ApiModelProperty(name = "orgId", dataType = "String", value = "机构ID编号")
	private String orgId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}
