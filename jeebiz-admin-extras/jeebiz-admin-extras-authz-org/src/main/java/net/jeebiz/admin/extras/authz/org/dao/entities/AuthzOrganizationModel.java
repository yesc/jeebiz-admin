package net.jeebiz.admin.extras.authz.org.dao.entities;

import org.apache.ibatis.type.Alias;

import net.jeebiz.boot.api.dao.entities.PaginationModel;

@Alias(value = "AuthzCompanyModel")
@SuppressWarnings("serial")
public class AuthzCompanyModel extends PaginationModel {

	/**
	 * 公司ID编号
	 */
	private String id;
	/**
	 * 公司编码
	 */
	private String code;
	/**
	 * 公司名称
	 */
	private String name;
	/**
	 * 公司说明
	 */
	private String remark;
	/**
	 * 父级公司ID编号
	 */
	private String parent;
	/**
	 * 公司创建人ID
	 */
	private String userId;
	/**
	 * 公司状态（0:禁用|1:可用）
	 */
	private String status;
	/**
	 * 公司创建时间
	 */
	private String time24;

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
