package net.jeebiz.admin.extras.logbiz.web.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.extras.logbiz.dao.entities.AuthzLogModel;
import net.jeebiz.admin.extras.logbiz.service.IAuthzLogService;
import net.jeebiz.admin.extras.logbiz.utils.enums.AuthzOptEnum;
import net.jeebiz.admin.extras.logbiz.utils.enums.LoggerLevelEnum;
import net.jeebiz.admin.extras.logbiz.web.vo.AuthzLogPaginationVo;
import net.jeebiz.admin.extras.logbiz.web.vo.AuthzLogVo;
import net.jeebiz.boot.api.webmvc.BaseMapperController;
import net.jeebiz.boot.api.webmvc.Result;

@Api(tags = "认证授权日志 : （用户登录、登出日志信息）")
@RestController
@RequestMapping("/extras/logs/authz/")
public class AuthzLogsController extends BaseMapperController {

	@Autowired
	private IAuthzLogService authzLogService;
	
	@ApiOperation(value = "认证授权类型", notes = "认证授权类型（login:登录认证、logout:会话注销）")
	@PostMapping("opts")
	@ResponseBody
	public Object authzOpts() throws Exception {
		return AuthzOptEnum.toList();
	}

	@ApiOperation(value = "日志级别", notes = "日志级别（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）")
	@PostMapping("levels")
	@ResponseBody
	public Object levels() throws Exception {
		return LoggerLevelEnum.toList();
	}
	
	@ApiOperation(value = "认证授权日志", notes = "分页查询用户登录、登出日志信息")
	@PostMapping("list")
	@RequiresPermissions("logs-authz:list")
	@ResponseBody
	public Object list(@Valid AuthzLogPaginationVo paginationVo)
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
