package net.jeebiz.admin.extras.inform.web.vo;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.SafeHtml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "InformVo", description = "消息通知传输对象")
public class InformVo {

	/**
	 * 消息通知ID编号
	 */
	@ApiModelProperty(value = "id", dataType = "String", notes = "消息通知ID编号")
	private String id;
	/**
	 * 消息通知通知对象ID
	 */
	@ApiModelProperty(value = "userId", dataType = "String", notes = "消息通知对象ID")
	private String userId;
	/**
	 * 消息通知标题
	 */
	@ApiModelProperty(value = "title", required = true, dataType = "String", notes = "消息通知标题")
	@NotBlank(message = "消息通知标题必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String title;
	/**
	 * 消息通知类型：（notice：通知、direct：私信）
	 */
	@ApiModelProperty(value = "type", dataType = "String", notes = "消息通知类型：（notice：通知、letter：私信）", allowableValues = "notice,letter")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String type;
	/**
	 * 消息通知内容
	 */
	@ApiModelProperty(value = "detail", required = true, dataType = "String", notes = "消息通知内容")
	@NotBlank(message = "消息通知内容必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String detail;
	/**
	 * 消息通知阅读状态：（0:未阅读、1:已阅读）
	 */
	@ApiModelProperty(value = "status", dataType = "String", notes = "消息通知阅读状态：（0:未阅读、1:已阅读）", allowableValues = "0,1")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String status;
	/**
	 * 消息通知送达时间
	 */
	private String timestamp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}
