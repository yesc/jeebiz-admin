package net.jeebiz.admin.extras.logbiz.web.vo;

import org.hibernate.validator.constraints.SafeHtml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.jeebiz.boot.api.vo.AbstractPaginationVo;

/**
 * 功能操作数据筛选条件Vo
 */
@ApiModel(value = "BizLogPaginationVo", description = "功能操作数据筛选条件Vo")
public class BizLogPaginationVo extends AbstractPaginationVo {

	/**
	 * 功能模块
	 */
	@ApiModelProperty(value = "module", dataType = "String", notes = "功能模块")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String module;
	/**
	 * 业务名称
	 */
	@ApiModelProperty(value = "business", dataType = "String", notes = "业务名称")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String business;
	/**
	 * 操作类型
	 */
	@ApiModelProperty(value = "opt", dataType = "String", notes = "操作类型", allowableValues = "login,logout,insert,delete,update,select,upload,download")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String opt;
	/**
	 * 日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）
	 */
	@ApiModelProperty(value = "level", dataType = "String", notes = "日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String level;
	/**
	 * 功能操作请求来源IP地址
	 */
	@ApiModelProperty(value = "addr", dataType = "String", notes = "功能操作请求来源IP地址")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String addr;
	/**
	 * 功能操作发生起始时间
	 */
	@ApiModelProperty(value = "begintime", dataType = "String", notes = "功能操作发生起始时间")
	private String begintime;
	/**
	 * 功能操作发生结束时间
	 */
	@ApiModelProperty(value = "endtime", dataType = "String", notes = "功能操作发生结束时间")
	private String endtime;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

}
