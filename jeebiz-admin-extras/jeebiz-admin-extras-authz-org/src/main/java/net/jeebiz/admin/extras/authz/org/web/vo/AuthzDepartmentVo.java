/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.web.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel(value = "AuthzDepartmentVo", description = "部门信息Vo")
public class AuthzDepartmentVo implements Serializable {

	/**
	 * 公司ID编号
	 */
	@ApiModelProperty(value = "comId", dataType = "String", notes = "公司ID编号")
	private String comId;
	/**
	 * 公司名称
	 */
	private String comName;
	/**
	 * 部门ID编号
	 */
	@ApiModelProperty(value = "id", dataType = "String", notes = "部门ID编号")
	private String id;
	/**
	 * 部门编码
	 */
	@ApiModelProperty(value = "code", dataType = "String", notes = "部门编码")
	private String code;
	/**
	 * 部门名称
	 */
	@ApiModelProperty(value = "name", dataType = "String", notes = "部门名称")
	private String name;
	/**
	 * 部门说明
	 */
	@ApiModelProperty(value = "remark", dataType = "String", notes = "部门说明")
	private String remark;
	/**
	 * 父级部门ID编号
	 */
	@ApiModelProperty(value = "parent", dataType = "String", notes = "父级部门ID编号")
	private String parent;
	/**
	 * 部门创建人ID
	 */
	@ApiModelProperty(value = "userId", dataType = "String", notes = "部门创建人ID")
	private String userId;
	/**
	 * 部门状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(value = "status", dataType = "String", notes = "部门状态（0:禁用|1:可用）")
	private String status;
	/**
	 * 部门创建时间
	 */
	@ApiModelProperty(value = "time24", dataType = "String", notes = "部门创建时间")
	private String time24;

	public void setComId(String comId) {
		this.comId = comId;
	}

	public String getComId() {
		return comId;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTime24() {
		return time24;
	}

	public void setTime24(String time24) {
		this.time24 = time24;
	}

}
