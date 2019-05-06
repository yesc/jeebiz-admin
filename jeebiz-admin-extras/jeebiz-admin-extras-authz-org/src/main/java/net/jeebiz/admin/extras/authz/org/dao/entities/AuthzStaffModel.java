/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.dao.entities;

import org.apache.ibatis.type.Alias;

import net.jeebiz.boot.api.dao.entities.PaginationModel;

@Alias(value = "AuthzStaffModel")
@SuppressWarnings("serial")
public class AuthzStaffModel extends PaginationModel {

	/**
	 * 机构ID编号
	 */
	private String orgId;
	/**
	 * 部门ID编号
	 */
	private String deptId;
	/**
	 * 岗位ID编号
	 */
	private String postId;
	/**
	 * 员工ID编号
	 */
	private String id;
	/**
	 * 员工简介
	 */
	private String intro;
	/**
	 * 员工状态（0:禁用|1:可用）
	 */
	private String status;
	/**
	 * 员工入职时间
	 */
	private String time24;
	
	/**
	 * 用户别名（昵称）
	 */
	private String alias;
	/**
	 * 用户头像：图片路径或图标样式
	 */
	private String avatar;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 电子邮箱
	 */
	private String email;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
