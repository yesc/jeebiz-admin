package net.jeebiz.admin.extras.basedata.web.vo;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.SafeHtml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "KeyValueRequestVo", description = "基础数据集合传输对象")
public class KeyValueRequestVo {

	/**
	 * 基础数据分组
	 */
	@ApiModelProperty(value = "gkey", dataType = "String", notes = "基础数据分组")
	@NotBlank(message = "基础数据分组必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String gkey;
	
	private List<KeyValueVo> datas;

	public String getGkey() {
		return gkey;
	}

	public void setGkey(String gkey) {
		this.gkey = gkey;
	}

	public List<KeyValueVo> getDatas() {
		return datas;
	}

	public void setDatas(List<KeyValueVo> datas) {
		this.datas = datas;
	}
	
}
