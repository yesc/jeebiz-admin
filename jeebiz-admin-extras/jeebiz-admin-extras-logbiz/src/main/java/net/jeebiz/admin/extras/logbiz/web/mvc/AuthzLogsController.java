/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.web.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.jeebiz.admin.extras.logbiz.dao.entities.AuthzLogModel;
import net.jeebiz.admin.extras.logbiz.service.IAuthzLogService;
import net.jeebiz.admin.extras.logbiz.utils.enums.AuthzOptEnum;
import net.jeebiz.admin.extras.logbiz.utils.enums.LoggerLevelEnum;
import net.jeebiz.admin.extras.logbiz.web.vo.AuthzLogPaginationVo;
import net.jeebiz.admin.extras.logbiz.web.vo.AuthzLogVo;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.webmvc.BaseMapperController;
import net.jeebiz.boot.api.webmvc.Result;

@Api(tags = "认证授权日志 : （用户登录、登出日志信息）")
@ApiResponses({ 
	@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_CREATED, message = "已创建", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = "请求要求身份验证", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "权限不足", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "请求资源不存在", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "服务器内部异常", response = ApiRestResponse.class)
})
@RestController
@RequestMapping("/extras/logs/authz/")
public class AuthzLogsController extends BaseMapperController {

	@Autowired
	private IAuthzLogService authzLogService;
	
	@ApiOperation(value = "认证授权类型", notes = "认证授权类型（login:登录认证、logout:会话注销）")
	@GetMapping("opts")
	@RequiresAuthentication
	@ResponseBody
	public Object authzOpts() throws Exception {
		return AuthzOptEnum.toList();
	}

	@ApiOperation(value = "日志级别", notes = "日志级别（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）")
	@GetMapping("levels")
	@RequiresAuthentication
	@ResponseBody
	public Object levels() throws Exception {
		return LoggerLevelEnum.toList();
	}
	
	@ApiOperation(value = "认证授权日志", notes = "分页查询用户登录、登出日志信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "数据筛选条件", dataType = "AuthzLogPaginationVo")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = Result.class)
	})
	@PostMapping("list")
	@RequiresPermissions("logs-authz:list")
	@ResponseBody
	public Object list(@Valid @RequestBody AuthzLogPaginationVo paginationVo)
			throws Exception {
		
		AuthzLogModel model = getBeanMapper().map(paginationVo, AuthzLogModel.class);
		Page<AuthzLogModel> pageResult = getAuthzLogService().getPagedList(model);
		List<AuthzLogVo> retList = new ArrayList<AuthzLogVo>();
		for (AuthzLogModel logModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(logModel, AuthzLogVo.class));
		}
		
		return new Result<AuthzLogVo>(pageResult, retList);
	}

	public IAuthzLogService getAuthzLogService() {
		return authzLogService;
	}

	public void setAuthzLogService(IAuthzLogService authzLogService) {
		this.authzLogService = authzLogService;
	}
	
}
