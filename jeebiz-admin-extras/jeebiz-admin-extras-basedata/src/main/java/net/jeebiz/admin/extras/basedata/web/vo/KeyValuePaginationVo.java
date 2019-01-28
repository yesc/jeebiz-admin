package net.jeebiz.admin.extras.basedata.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.jeebiz.boot.api.vo.AbstractPaginationVo;

@ApiModel(value = "KeyValuePaginationVo", description = "基础数据分页查询参数Vo")
public class KeyValuePaginationVo extends AbstractPaginationVo {
	
	/**
	 * 基础数据分组
	 */
	@ApiModelProperty(value = "gkey", dataType = "String", notes = "基础数据分组")
	private String gkey;

	public String getGkey() {
		return gkey;
	}

	public void setGkey(String gkey) {
		this.gkey = gkey;
	}

}
