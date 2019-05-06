package net.jeebiz.admin.extras.authz.org.web.vo;

import org.hibernate.validator.constraints.SafeHtml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.jeebiz.boot.api.vo.AbstractPaginationVo;

@ApiModel(value = "AuthzCompanyPaginationVo", description = "公司信息分页查询参数")
public class AuthzCompanyPaginationVo extends AbstractPaginationVo {

	/**
	 * 公司名称
	 */
	@ApiModelProperty(value = "name", dataType = "String", notes = "公司单位名称")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
