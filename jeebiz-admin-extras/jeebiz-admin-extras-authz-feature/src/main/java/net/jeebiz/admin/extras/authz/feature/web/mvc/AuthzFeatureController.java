/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.feature.web.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureOptModel;
import net.jeebiz.admin.extras.authz.feature.service.IAuthzFeatureOptService;
import net.jeebiz.admin.extras.authz.feature.service.IAuthzFeatureService;
import net.jeebiz.admin.extras.authz.feature.setup.handler.FeatureDataHandlerFactory;
import net.jeebiz.admin.extras.authz.feature.web.vo.AuthzFeatureVo;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.exception.ApiRestResponse;
import net.jeebiz.boot.api.utils.Constants;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.utils.ResultUtils;
import net.jeebiz.boot.api.webmvc.BaseMapperController;
import net.jeebiz.boot.api.webmvc.Result;

@Api(tags = "功能菜单：数据维护（Ok）")
@ApiResponses({ 
	@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_CREATED, message = "已创建", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = "请求要求身份验证", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "权限不足", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "请求资源不存在", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "服务器内部异常", response = ApiRestResponse.class)
})
@RestController
@RequestMapping(value = "/extras/feature/")
public class AuthzFeatureController extends BaseMapperController{

	@Autowired
	protected IAuthzFeatureService authzFeatureService;
	@Autowired
	protected IAuthzFeatureOptService authzFeatureOptService;
	
	@ApiOperation(value = "查询功能菜单树形结构数据", notes = "查询功能菜单树形结构数据")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "featureVo", value = "功能菜单信息筛选条件", dataType = "ExtrasFeatureVo")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = Result.class),
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单树形结构数据", opt = BusinessType.SELECT)
	@RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
	@RequiresPermissions("feature:list")
	@ResponseBody
	public Object list(@Valid @RequestBody AuthzFeatureVo featureVo){
		
		AuthzFeatureModel model = getBeanMapper().map(featureVo, AuthzFeatureModel.class);
		List<AuthzFeatureModel> pageResult = getAuthzFeatureService().getModelList(model);
		List<AuthzFeatureVo> retList = new ArrayList<AuthzFeatureVo>();
		for (AuthzFeatureModel optModel : pageResult) {
			retList.add(getBeanMapper().map(optModel, AuthzFeatureVo.class));
		}
		return new Result<AuthzFeatureVo>(retList);
		
	}
	
	@ApiOperation(value = "查询功能菜单树形结构数据", notes = "查询功能菜单树形结构数据")
	@ApiImplicitParams({ 
		@ApiImplicitParam( name = "handler", value = "数据处理实现对象名称", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单树形结构数据", opt = BusinessType.SELECT)
	@PostMapping("tree")
	@RequiresPermissions("feature:list")
	@ResponseBody
	public Object tree(@RequestParam(required = false) String handler){
		// 所有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 所有的功能操作按钮
		List<AuthzFeatureOptModel> featureOptList = getAuthzFeatureOptService().getFeatureOpts();
		// 返回各级菜单 + 对应的功能权限数据
		return ResultUtils.dataMap(FeatureDataHandlerFactory.getTreeHandler(handler).handle(featureList, featureOptList));
	}
	
	@ApiOperation(value = "查询功能菜单树扁平构数据", notes = "查询功能菜单树扁平构数据")
	@ApiImplicitParams({ 
		@ApiImplicitParam( name = "handler", value = "数据处理实现对象名称", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单扁平结构数据", opt = BusinessType.SELECT)
	@PostMapping("flat")
	@RequiresPermissions("feature:list")
	@ResponseBody
	public Object flat(@RequestParam(required = false) String handler){
		// 所有的功能菜单
		List<AuthzFeatureModel> featureList = getAuthzFeatureService().getFeatureList();
		// 所有的功能操作按钮
		List<AuthzFeatureOptModel> featureOptList = getAuthzFeatureOptService().getFeatureOpts();
		// 返回叶子节点菜单 + 对应的功能权限数据
		return ResultUtils.dataMap(FeatureDataHandlerFactory.getFlatHandler(handler).handle(featureList, featureOptList));
	}
	
	@ApiOperation(value = "增加功能菜单信息", notes = "增加功能菜单信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "featureVo", value = "功能菜单信息", dataType = "ExtrasFeatureVo")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "新增功能菜单-名称：${name}", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("feature:new")
	@ResponseBody
	public Object newFeature(@Valid @RequestBody AuthzFeatureVo featureVo) throws Exception { 
		AuthzFeatureModel model = getBeanMapper().map(featureVo, AuthzFeatureModel.class);
		int total = getAuthzFeatureService().insert(model);
		if(total > 0) {
			return success("feature.new.success", total);
		}
		return fail("feature.new.fail", total);
	}
	
	@ApiOperation(value = "修改功能菜单信息", notes = "修改功能菜单信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "featureVo", value = "功能菜单信息", dataType = "ExtrasFeatureVo")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "修改功能菜单-名称：${name}", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("feature:renew")
	@ResponseBody
	public Object renewFeature(@Valid @RequestBody AuthzFeatureVo featureVo) throws Exception { 
		AuthzFeatureModel model = getBeanMapper().map(featureVo, AuthzFeatureModel.class);
		int total = getAuthzFeatureService().update(model);
		if(total > 0) {
			return success("feature.renew.success", total);
		}
		return fail("feature.renew.fail", total);
	}
	
	@ApiOperation(value = "根据功能菜单ID查询功能菜单信息", notes = "根据功能菜单ID查询功能菜单信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( name = "id", required = true, value = "功能菜单ID", dataType = "String")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = AuthzFeatureVo.class)
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单-ID：${featureid}", opt = BusinessType.SELECT)
	@PostMapping("detail/{id}")
	@RequiresPermissions("feature:detail")
	@ResponseBody
	public Object detail(@PathVariable("id") String id) throws Exception {
		AuthzFeatureModel model = getAuthzFeatureService().getModel(id);
		if( model == null) {
			return ApiRestResponse.empty(getMessage("feature.get.empty"));
		}
		return getBeanMapper().map(model, AuthzFeatureVo.class);
	}
	
	@ApiOperation(value = "删除功能菜单信息", notes = "删除功能菜单信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( name = "featureid", required = true, value = "功能菜单ID", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_FEATURE, business = "删除功能菜单-名称：${featureid}", opt = BusinessType.DELETE)
	@PostMapping("delete/{id}")
	@RequiresPermissions("feature:delete")
	@ResponseBody
	public Object delFeature(@PathVariable String id) throws Exception { 
		int total = getAuthzFeatureService().delete(id);
		if(total > 0) {
			return success("feature.delete.success", total);
		}
		return success("feature.delete.fail", total);
	}
	
	public IAuthzFeatureService getAuthzFeatureService() {
		return authzFeatureService;
	}

	public void setAuthzFeatureService(IAuthzFeatureService authzFeatureService) {
		this.authzFeatureService = authzFeatureService;
	}

	public IAuthzFeatureOptService getAuthzFeatureOptService() {
		return authzFeatureOptService;
	}

	public void setAuthzFeatureOptService(IAuthzFeatureOptService authzFeatureOptService) {
		this.authzFeatureOptService = authzFeatureOptService;
	}
	
}
