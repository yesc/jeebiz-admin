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
	@ApiModelProperty(value = "name", dataType = "String", notes = "部门名称")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String name;
	/**
	 * 公司ID编号
	 */
	@ApiModelProperty(value = "comId", dataType = "String", notes = "公司ID编号")
	private String comId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComId() {
		return comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}
	
}
