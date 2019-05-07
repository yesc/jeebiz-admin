/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.web.mvc;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzPositionModel;
import net.jeebiz.admin.extras.authz.org.service.IAuthzPositionService;
import net.jeebiz.admin.extras.authz.org.setup.Constants;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzPositionNewVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzPositionPaginationVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzPositionRenewVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzPositionVo;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.webmvc.BaseMapperController;
import net.jeebiz.boot.api.webmvc.Result;

@Api(tags = "岗位管理：岗位信息维护")
@RestController
@RequestMapping(value = "/authz/post/")
public class AuthzPositionController extends BaseMapperController {
	
	@Autowired
	private IAuthzPositionService authzPositionService;

	@ApiOperation(value = "分页查询岗位信息", notes = "分页查询岗位信息")
	@BusinessLog(module = Constants.AUTHZ_POST, business = "分页查询岗位信息", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("authz-post:list")
	@ResponseBody
	public Object list(@Valid AuthzPositionPaginationVo paginationVo) throws Exception {
		
		AuthzPositionModel model = getBeanMapper().map(paginationVo, AuthzPositionModel.class);
		Page<AuthzPositionModel> pageResult = getAuthzPositionService().getPagedList(model);
		List<AuthzPositionVo> retList = Lists.newArrayList();
		for (AuthzPositionModel postModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(postModel, AuthzPositionVo.class));
		}
		
		return new Result<AuthzPositionVo>(pageResult, retList);
		
	}
	
	@ApiOperation(value = "查询岗位信息", notes = "查询岗位信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "group", value = "岗位信息分组", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "查询岗位信息", opt = BusinessType.SELECT)
	@PostMapping("pairs")
	@RequiresPermissions("authz-post:list")
	@ResponseBody
	public Object pairs(@RequestParam String group) throws Exception {
		return getAuthzPositionService().getPairValues(group);
	}
	
	@ApiOperation(value = "创建岗位信息", notes = "增加一个新的岗位信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "postVo", value = "岗位信息传输对象", required = true, dataType = "AuthzPositionNewVo") 
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "创建岗位信息", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("authz-post:new")
	@ResponseBody
	public Object position(@Valid @RequestBody AuthzPositionNewVo postVo) throws Exception {
		AuthzPositionModel model = getBeanMapper().map(postVo, AuthzPositionModel.class);
		model.setUserId(SubjectUtils.getPrincipal(ShiroPrincipal.class).getUserid());
		// 新增一条数据库配置记录
		int result = getAuthzPositionService().insert(model);
		if(result > 0) {
			return success("authz.post.new.success", result);
		}
		return fail("authz.post.new.fail", result);
	}
	
	@ApiOperation(value = "更新岗位信息", notes = "更新岗位信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "postVo", value = "岗位信息", required = true, dataType = "AuthzPositionRenewVo"),
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "更新岗位信息", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("authz-post:renew")
	@ResponseBody
	public Object renew(@Valid @RequestBody AuthzPositionRenewVo postVo) throws Exception {
		AuthzPositionModel model = getBeanMapper().map(postVo, AuthzPositionModel.class);
		int result = getAuthzPositionService().update(model);
		if(result == 1) {
			return success("authz.post.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz-post.renew.fail", result);
	}
	
	@ApiOperation(value = "更新岗位信息状态", notes = "更新岗位信息状态")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "岗位信息ID", dataType = "String"),
		@ApiImplicitParam(name = "status", required = true, value = "岗位信息状态", dataType = "String", allowableValues = "1,0")
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "更新岗位信息状态", opt = BusinessType.UPDATE)
	@GetMapping(value = "status")
	@RequiresPermissions(value = {"authz-post:enable", "authz-post:disable"}, logical = Logical.OR)
	@ResponseBody
	public Object status(@RequestParam String id, @RequestParam String status) throws Exception {
		int result = getAuthzPositionService().setStatus(id, status);
		if(result == 1) {
			return success("authz.post.status.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.post.status.fail", result);
	}
	
	@ApiOperation(value = "删除岗位信息", notes = "删除岗位信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", value = "岗位信息ID", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "删除岗位信息", opt = BusinessType.UPDATE)
	@GetMapping("delete/{id}")
	@RequiresPermissions("authz-post:delete")
	@ResponseBody
	public Object delete(@PathVariable("id") String id) throws Exception {
		// 执行岗位信息删除操作
		int result = getAuthzPositionService().delete(id);
		if(result > 0) {
			return success("authz.post.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.post.delete.fail", result);
	}
	
	@ApiOperation(value = "查询岗位信息", notes = "根据ID查询岗位信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( name = "id", required = true, value = "岗位信息ID", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_POST, business = "查询岗位信息", opt = BusinessType.SELECT)
	@GetMapping("detail/{id}")
	@RequiresPermissions(value = {"authz-post:list" ,"authz-post:detail" }, logical = Logical.OR)
	@ResponseBody
	public Object detail(@PathVariable("id") String id) throws Exception { 
		AuthzPositionModel model = getAuthzPositionService().getModel(id);
		if( model == null) {
			
		}
		return getBeanMapper().map(model, AuthzPositionVo.class);
	}

	public IAuthzPositionService getAuthzPositionService() {
		return authzPositionService;
	}

	public void setAuthzPositionService(IAuthzPositionService authzPositionService) {
		this.authzPositionService = authzPositionService;
	}

}
