package net.jeebiz.admin.extras.authz.org.web.mvc;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzCompanyModel;
import net.jeebiz.admin.extras.authz.org.service.IAuthzCompanyService;
import net.jeebiz.admin.extras.authz.org.setup.Constants;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzCompanyPaginationVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzCompanyVo;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.webmvc.BaseMapperController;
import net.jeebiz.boot.api.webmvc.Result;

@Api(tags = "单位管理：单位信息维护")
@Controller
@RequestMapping(value = "/authz/org/")
public class AuthzCompanyController extends BaseMapperController {
	
	@Autowired
	private IAuthzCompanyService authzCompanyService;

	@ApiOperation(value = "根据分组分页查询单位信息", notes = "根据分组分页查询单位信息")
	@BusinessLog(module = Constants.AUTHZ_COMPANY, business = "根据分组分页查询单位信息", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("company:list")
	@ResponseBody
	public Object list(@Valid AuthzCompanyPaginationVo paginationVo) throws Exception {
		
		AuthzCompanyModel model = getBeanMapper().map(paginationVo, AuthzCompanyModel.class);
		List<AuthzCompanyModel> pageResult = getAuthzCompanyService().getModelList(model);
		List<AuthzCompanyVo> retList = Lists.newArrayList();
		for (AuthzCompanyModel companyModel : pageResult) {
			retList.add(getBeanMapper().map(companyModel, AuthzCompanyVo.class));
		}
		
		return new Result<AuthzCompanyVo>(retList);
		
	}
	
	@ApiOperation(value = "根据分组查询单位信息", notes = "根据分组查询单位信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "group", value = "单位信息分组", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_COMPANY, business = "根据分组查询单位信息", opt = BusinessType.SELECT)
	@PostMapping("pairs")
	@RequiresPermissions("company:list")
	@ResponseBody
	public Object pairs(@RequestParam String group) throws Exception {
		return getAuthzCompanyService().getPairValues(group);
	}
	
	@ApiOperation(value = "创建单位信息", notes = "增加一个新的单位信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "companyVo", value = "单位信息传输对象", dataType = "AuthzCompanyVo") 
	})
	@BusinessLog(module = Constants.AUTHZ_COMPANY, business = "创建单位信息", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("company:new")
	@ResponseBody
	public Object newCompany(@Valid @RequestBody AuthzCompanyVo companyVo) throws Exception {
		AuthzCompanyModel model = getBeanMapper().map(companyVo, AuthzCompanyModel.class);
		model.setUserId(SubjectUtils.getPrincipal(ShiroPrincipal.class).getUserid());
		// 新增一条数据库配置记录
		int result = getAuthzCompanyService().insert(model);
		if(result > 0) {
			return success("company.new.success", result);
		}
		return fail("company.new.fail", result);
	}
	
	@ApiOperation(value = "更新单位信息", notes = "更新单位信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "companyVo", value = "单位信息", required = true, dataType = "AuthzCompanyVo"),
	})
	@BusinessLog(module = Constants.AUTHZ_COMPANY, business = "更新单位信息", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("company:renew")
	@ResponseBody
	public Object renew(@Valid @RequestBody AuthzCompanyVo companyVo) throws Exception {
		AuthzCompanyModel model = getBeanMapper().map(companyVo, AuthzCompanyModel.class);
		int result = getAuthzCompanyService().update(model);
		if(result == 1) {
			return success("company.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("company.renew.fail", result);
	}
	
	@ApiOperation(value = "更新单位信息状态", notes = "更新单位信息状态")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "单位信息ID", dataType = "String"),
		@ApiImplicitParam(name = "status", required = true, value = "单位信息状态", dataType = "String", allowableValues = "1,0")
	})
	@BusinessLog(module = Constants.AUTHZ_COMPANY, business = "更新单位信息状态", opt = BusinessType.UPDATE)
	@PostMapping(value = "status")
	@RequiresPermissions(value = {"company:enable", "company:disable"}, logical = Logical.OR)
	@ResponseBody
	public Object status(@RequestParam String id, @RequestParam String status) throws Exception {
		int result = getAuthzCompanyService().setStatus(id, status);
		if(result == 1) {
			return success("company.status.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("company.status.fail", result);
	}
	
	@ApiOperation(value = "删除单位信息", notes = "删除单位信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", value = "单位信息ID", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_COMPANY, business = "删除单位信息", opt = BusinessType.UPDATE)
	@PostMapping("delete/{id}")
	@RequiresPermissions("company:delete")
	@ResponseBody
	public Object delete(@PathVariable("id") String id) throws Exception {
		// 执行单位信息删除操作
		int result = getAuthzCompanyService().delete(id);
		if(result > 0) {
			return success("company.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("company.delete.fail", result);
	}
	
	@ApiOperation(value = "查询单位信息", notes = "根据ID查询单位信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( name = "id", required = true, value = "单位信息ID", dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_COMPANY, business = "查询单位信息", opt = BusinessType.SELECT)
	@PostMapping("detail/{id}")
	@RequiresPermissions(value = {"company:list" ,"company:detail" }, logical = Logical.OR)
	@ResponseBody
	public Object detail(@PathVariable("id") String id) throws Exception { 
		return getAuthzCompanyService().getModel(id);
	}

	public IAuthzCompanyService getAuthzCompanyService() {
		return authzCompanyService;
	}

	public void setAuthzCompanyService(IAuthzCompanyService authzCompanyService) {
		this.authzCompanyService = authzCompanyService;
	}

}
