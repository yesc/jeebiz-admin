/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.web.vo;

import java.io.Serializable;

import org.hibernate.validator.constraints.SafeHtml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AuthzOrganizationNewVo", description = "新增机构信息参数Vo")
@SuppressWarnings("serial")
public class AuthzOrganizationNewVo implements Serializable {

	/**
	 * 机构编码
	 */
	@ApiModelProperty(name = "code", required = true, dataType = "String", value = "机构编码")
	private String code;
	/**
	 * 机构名称
	 */
	@ApiModelProperty(name = "name", required = true, dataType = "String", value = "机构名称")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String name;
	/**
	 * 机构简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "机构简介")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String intro;
	/**
	 * 父级机构ID编号
	 */
	@ApiModelProperty(name = "parent", dataType = "String", value = "父级机构ID编号")
	private String parent;

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

}
