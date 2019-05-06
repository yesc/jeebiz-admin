package net.jeebiz.admin.extras.authz.org.web.mvc;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzOrganizationModel;
import net.jeebiz.admin.extras.authz.org.service.IAuthzOrganizationService;
import net.jeebiz.admin.extras.authz.org.setup.Constants;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzOrganizationPaginationVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzOrganizationVo;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.webmvc.BaseMapperController;
import net.jeebiz.boot.api.webmvc.Result;

@Api(tags = "组织机构管理：机构信息维护")
@RestController
@RequestMapping(value = "/authz/org/")
public class AuthzOrganizationController extends BaseMapperController {
	
	@Autowired
	private IAuthzOrganizationService authzOrganizationService;

	@ApiOperation(value = "根据分组分页查询机构信息", notes = "根据分组分页查询机构信息")
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
	
	@ApiOperation(value = "根据分组查询机构信息", notes = "根据分组查询机构信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "group", value = "机构信息分组", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "根据分组查询机构信息", opt = BusinessType.SELECT)
	@PostMapping("pairs")
	@RequiresPermissions("authz-org:list")
	@ResponseBody
	public Object pairs(@RequestParam String group) throws Exception {
		return getAuthzOrganizationService().getPairValues(group);
	}
	
	@ApiOperation(value = "创建机构信息", notes = "增加一个新的机构信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "orgVo", value = "机构信息传输对象", dataType = "AuthzCompanyVo") 
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "创建机构信息", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("authz-org:new")
	@ResponseBody
	public Object newOrg(@Valid @RequestBody AuthzOrganizationVo orgVo) throws Exception {
		AuthzOrganizationModel model = getBeanMapper().map(orgVo, AuthzOrganizationModel.class);
		model.setUserId(SubjectUtils.getPrincipal(ShiroPrincipal.class).getUserid());
		// 新增一条数据库配置记录
		int result = getAuthzOrganizationService().insert(model);
		if(result > 0) {
			return success("authz.org.new.success", result);
		}
		return fail("authz.org.new.fail", result);
	}
	
	@ApiOperation(value = "更新机构信息", notes = "更新机构信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "orgVo", value = "机构信息", required = true, dataType = "AuthzCompanyVo"),
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "更新机构信息", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("authz-org:renew")
	@ResponseBody
	public Object renew(@Valid @RequestBody AuthzOrganizationVo orgVo) throws Exception {
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
	@PostMapping(value = "status")
	@RequiresPermissions(value = {"authz-org:enable", "authz-org:disable"}, logical = Logical.OR)
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
	@PostMapping("delete/{id}")
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
	
	@ApiOperation(value = "查询机构信息", notes = "根据ID查询机构信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( name = "id", required = true, value = "机构信息ID", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_ORG, business = "查询机构信息", opt = BusinessType.SELECT)
	@PostMapping("detail/{id}")
	@RequiresPermissions(value = {"authz-org:list" ,"authz-org:detail" }, logical = Logical.OR)
	@ResponseBody
	public Object detail(@PathVariable("id") String id) throws Exception { 
		return getAuthzOrganizationService().getModel(id);
	}

	public IAuthzOrganizationService getAuthzOrganizationService() {
		return authzOrganizationService;
	}

	public void setAuthzOrganizationService(IAuthzOrganizationService authzOrganizationService) {
		this.authzOrganizationService = authzOrganizationService;
	}

}
