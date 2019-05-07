/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.web.vo;

import java.io.Serializable;

import org.hibernate.validator.constraints.SafeHtml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel(value = "AuthzDepartmentRenewVo", description = "部门信息更新参数Vo")
public class AuthzDepartmentRenewVo implements Serializable {

	/**
	 * 公司ID编号
	 */
	@ApiModelProperty(value = "comId", required = true, dataType = "String", notes = "公司ID编号")
	private String comId;
	/**
	 * 部门ID编号
	 */
	@ApiModelProperty(value = "id", required = true, dataType = "String", notes = "部门ID编号")
	private String id;
	/**
	 * 部门编码
	 */
	@ApiModelProperty(value = "code", required = true, dataType = "String", notes = "部门编码")
	private String code;
	/**
	 * 部门名称
	 */
	@ApiModelProperty(value = "name", required = true, dataType = "String", notes = "部门名称")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String name;
	/**
	 * 部门简介
	 */
	@ApiModelProperty(value = "intro", dataType = "String", notes = "部门简介")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String intro;
	/**
	 * 父级部门ID编号
	 */
	@ApiModelProperty(value = "parent", dataType = "String", notes = "父级部门ID编号")
	private String parent;
	/**
	 * 部门状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(value = "status", required = true, dataType = "String", notes = "部门状态（0:禁用|1:可用）", allowableValues = "1,0")
	private String status;

	public void setComId(String comId) {
		this.comId = comId;
	}

	public String getComId() {
		return comId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
