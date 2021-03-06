/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.feature.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AuthzFeatureOptVo", description = "功能操作参数Vo")
public class AuthzFeatureOptVo implements Comparable<AuthzFeatureOptVo> {
	
	/**
	 * 功能菜单ID
	 */
	@ApiModelProperty(name = "featureId", dataType = "String", value = "功能菜单ID")
	private String featureId;
	/**
	 * 功能操作ID
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "功能操作ID")
	private String id;
	/**
	 * 功能操作名称
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "功能操作名称")
	private String name;
	/**
	 * 功能操作图标样式
	 */
	@ApiModelProperty(name = "icon", dataType = "String", value = "功能操作图标样式")
	private String icon;
	/**
	 * 功能操作排序
	 */
	@ApiModelProperty(name = "order", dataType = "String", value = "功能操作排序")
	private String order;
	/**
	 * 功能操作是否可见(1:可见|0:不可见)
	 */
	@ApiModelProperty(name = "visible", dataType = "String", value = "功能操作是否可见(1:可见|0:不可见)", allowableValues = "1,0")
	private String visible;
	/**
	 * 功能操作是否授权(true:已授权|false:未授权)
	 */
	@ApiModelProperty(name = "checked", dataType = "Boolean", value = "功能操作是否授权(true:已授权|false:未授权)", allowableValues = "true,false")
	private boolean checked;
	/**
	 * 功能操作权限标记
	 */
	@ApiModelProperty(name = "perms", dataType = "String", value = "功能操作权限标记")
	private String perms;

	public String getFeatureId() {
		return featureId;
	}

	public void setFeatureId(String featureId) {
		this.featureId = featureId;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	@Override
	public int compareTo(AuthzFeatureOptVo o) {
		return Integer.parseInt(order) - Integer.parseInt(o.getOrder());
	}
	
}
