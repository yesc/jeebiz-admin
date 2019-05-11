/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.web.mvc;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzOrganizationModel;
import net.jeebiz.admin.extras.authz.org.service.IAuthzOrganizationService;
import net.jeebiz.admin.extras.authz.org.setup.Constants;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzOrganizationNewVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzOrganizationPaginationVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzOrganizationRenewVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzOrganizationVo;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.webmvc.BaseMapperController;
import net.jeebiz.boot.api.webmvc.Result;

@Api(tags = "组织机构：机构信息维护")
@ApiResponses({ 
	@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_CREATED, message = "已创建", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = "请求要求身份验证", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "权限不足", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "请求资源不存在", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "服务器内部异常", response = ApiRestResponse.class)
})
@RestController
@RequestMapping(value = "/authz/org/")
public class AuthzOrganizationController extends BaseMapperController {
	
	@Autowired
	private IAuthzOrganizationService authzOrganizationService;

	@ApiOperation(value = "根据分组分页查询机构信息", notes = "根据分组分页查询机构信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "分页查询参数", dataType = "AuthzOrganizationPaginationVo") 
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = Result.class)
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "根据分组分页查询机构信息", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("authz-org:list")
	@ResponseBody
	public Object list(@Valid AuthzOrganizationPaginationVo paginationVo) throws Exception {
		
		AuthzOrganizationModel model = getBeanMapper().map(paginationVo, AuthzOrganizationModel.class);
		List<AuthzOrganizationModel> pageResult = getAuthzOrganizationService().getModelList(model);
		List<AuthzOrganizationVo> retList = Lists.newArrayList();
		for (AuthzOrganizationModel orgModel : pageResult) {
			retList.add(getBeanMapper().map(orgModel, AuthzOrganizationVo.class));
		}
		
		return new Result<AuthzOrganizationVo>(retList);
		
	}
	
	@ApiOperation(value = "查询机构信息列表", notes = "查询机构信息列表")
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = PairModel.class, responseContainer = "List")
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "根据分组查询机构信息", opt = BusinessType.SELECT)
	@GetMapping("pairs")
	@RequiresPermissions("authz-org:list")
	@ResponseBody
	public Object pairs() throws Exception {
		return getAuthzOrganizationService().getPairValues("");
	}
	
	@ApiOperation(value = "创建机构信息", notes = "增加一个新的机构信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "orgVo", value = "机构信息传输对象", required = true, dataType = "AuthzOrganizationNewVo") 
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "创建机构信息", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("authz-org:new")
	@ResponseBody
	public Object newOrg(@Valid @RequestBody AuthzOrganizationNewVo orgVo) throws Exception {
		AuthzOrganizationModel model = getBeanMapper().map(orgVo, AuthzOrganizationModel.class);
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		model.setUserId(principal.getUserid());
		// 新增一条数据库配置记录
		int result = getAuthzOrganizationService().insert(model);
		if(result > 0) {
			return success("authz.org.new.success", result);
		}
		return fail("authz.org.new.fail", result);
	}
	
	@ApiOperation(value = "更新机构信息", notes = "更新机构信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "orgVo", value = "机构信息", required = true, dataType = "AuthzOrganizationRenewVo"),
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "更新机构信息", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("authz-org:renew")
	@ResponseBody
	public Object renew(@Valid @RequestBody AuthzOrganizationRenewVo orgVo) throws Exception {
		AuthzOrganizationModel model = getBeanMapper().map(orgVo, AuthzOrganizationModel.class);
		int result = getAuthzOrganizationService().update(model);
		if(result == 1) {
			return success("authz.org.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.org.renew.fail", result);
	}
	
	@ApiOperation(value = "更新机构信息状态", notes = "更新机构信息状态")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "机构信息ID", dataType = "String"),
		@ApiImplicitParam(name = "status", required = true, value = "机构信息状态", dataType = "String", allowableValues = "1,0")
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "更新机构信息状态", opt = BusinessType.UPDATE)
	@GetMapping(value = "status")
	@RequiresPermissions("authz-org:status")
	@ResponseBody
	public Object status(@RequestParam String id, @RequestParam String status) throws Exception {
		int result = getAuthzOrganizationService().setStatus(id, status);
		if(result == 1) {
			return success("authz.org.status.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.org.status.fail", result);
	}
	
	@ApiOperation(value = "删除机构信息", notes = "删除机构信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", value = "机构信息ID", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "删除机构信息", opt = BusinessType.UPDATE)
	@GetMapping("delete/{id}")
	@RequiresPermissions("authz-org:delete")
	@ResponseBody
	public Object delete(@PathVariable("id") String id) throws Exception {
		// 执行机构信息删除操作
		int result = getAuthzOrganizationService().delete(id);
		if(result > 0) {
			return success("authz.org.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.org.delete.fail", result);
	}
	
	@ApiOperation(value = "根据ID查询机构信息", notes = "根据ID查询机构信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( name = "id", required = true, value = "机构信息ID", dataType = "String")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = AuthzOrganizationVo.class)
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "查询机构信息", opt = BusinessType.SELECT)
	@GetMapping("detail/{id}")
	@RequiresPermissions("authz-org:detail")
	@ResponseBody
	public Object detail(@PathVariable("id") String id) throws Exception { 
		AuthzOrganizationModel model = getAuthzOrganizationService().getModel(id);
		if( model == null) {
			return ApiRestResponse.empty(getMessage("authz.org.get.empty"));
		}
		return getBeanMapper().map(model, AuthzOrganizationVo.class);
	}

	public IAuthzOrganizationService getAuthzOrganizationService() {
		return authzOrganizationService;
	}

	public void setAuthzOrganizationService(IAuthzOrganizationService authzOrganizationService) {
		this.authzOrganizationService = authzOrganizationService;
	}

}
