package net.jeebiz.admin.extras.authz.org.web.mvc;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzStaffModel;
import net.jeebiz.admin.extras.authz.org.service.IAuthzStaffService;
import net.jeebiz.admin.extras.authz.org.setup.Constants;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzStaffNewVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzStaffPaginationVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzStaffRenewVo;
import net.jeebiz.admin.extras.authz.org.web.vo.AuthzStaffVo;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.utils.HttpStatus;
import net.jeebiz.boot.api.webmvc.BaseMapperController;
import net.jeebiz.boot.api.webmvc.Result;

@Api(tags = "组织机构：人员信息维护")
@ApiResponses({ 
	@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_CREATED, message = "已创建", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = "请求要求身份验证", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "权限不足", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "请求资源不存在", response = ApiRestResponse.class),
	@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "服务器内部异常", response = ApiRestResponse.class)
})
@RestController
@RequestMapping(value = "/authz/org/staff/")
public class AuthzStaffController extends BaseMapperController {
	
	@Autowired
	private IAuthzStaffService authzStaffService;

	@ApiOperation(value = "分页查询员工信息", notes = "分页查询员工信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "分页查询参数", dataType = "AuthzStaffPaginationVo") 
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = Result.class)
	})
	@BusinessLog(module = Constants.AUTHZ_STAFF, business = "分页查询员工信息", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("authz-staff:list")
	@ResponseBody
	public Object list(@Valid @RequestBody AuthzStaffPaginationVo paginationVo) throws Exception {
		
		AuthzStaffModel model = getBeanMapper().map(paginationVo, AuthzStaffModel.class);
		Page<AuthzStaffModel> pageResult = getAuthzStaffService().getPagedList(model);
		List<AuthzStaffVo> retList = Lists.newArrayList();
		for (AuthzStaffModel staffModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(staffModel, AuthzStaffVo.class));
		}
		
		return new Result<AuthzStaffVo>(pageResult, retList);
		
	}
	
	@ApiOperation(value = "创建员工信息", notes = "增加一个新的员工信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "staffVo", value = "员工信息", required = true, dataType = "AuthzStaffNewVo") 
	})
	@BusinessLog(module = Constants.AUTHZ_STAFF, business = "创建员工信息", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("authz-staff:new")
	@ResponseBody
	public Object staff(@Valid @RequestBody AuthzStaffNewVo staffVo) throws Exception {
		AuthzStaffModel model = getBeanMapper().map(staffVo, AuthzStaffModel.class);
		int result = getAuthzStaffService().insert(model);
		if(result > 0) {
			return success("authz.staff.new.success", result);
		}
		return fail("authz.staff.new.fail", result);
	}
	
	@ApiOperation(value = "更新员工信息", notes = "更新员工信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "staffVo", value = "员工信息", required = true, dataType = "AuthzStaffRenewVo"),
	})
	@BusinessLog(module = Constants.AUTHZ_STAFF, business = "更新员工信息", opt = BusinessType.UPDATE)
	@PostMapping("renew")
	@RequiresPermissions("authz-staff:renew")
	@ResponseBody
	public Object renew(@Valid @RequestBody AuthzStaffRenewVo staffVo) throws Exception {
		AuthzStaffModel model = getBeanMapper().map(staffVo, AuthzStaffModel.class);
		int result = getAuthzStaffService().update(model);
		if(result == 1) {
			return success("authz.staff.renew.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.staff.renew.fail", result);
	}
	
	@ApiOperation(value = "更新员工信息状态", notes = "更新员工信息状态")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "员工ID编码", dataType = "String"),
		@ApiImplicitParam(name = "status", required = true, value = "员工信息状态", dataType = "String", allowableValues = "1,0")
	})
	@BusinessLog(module = Constants.AUTHZ_STAFF, business = "更新员工信息状态", opt = BusinessType.UPDATE)
	@GetMapping("status")
	@RequiresPermissions("authz-staff:status")
	@ResponseBody
	public Object status(@RequestParam String id, @RequestParam String status) throws Exception {
		int result = getAuthzStaffService().setStatus(id, status);
		if(result == 1) {
			return success("authz.staff.status.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.staff.status.fail", result);
	}
	
	@ApiOperation(value = "删除员工信息", notes = "删除员工信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "path", name = "id", value = "员工ID编码", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.AUTHZ_STAFF, business = "删除员工信息", opt = BusinessType.UPDATE)
	@GetMapping("delete/{id}")
	@RequiresPermissions("authz-staff:delete")
	@ResponseBody
	public Object delete(@PathVariable("id") String id) throws Exception {
		// 执行员工信息删除操作
		int result = getAuthzStaffService().delete(id);
		if(result > 0) {
			return success("authz.staff.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("authz.staff.delete.fail", result);
	}
	
	@ApiOperation(value = "查询员工信息", notes = "根据员工ID编码查询员工信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "path", name = "id", required = true, value = "员工ID编码", dataType = "String")
	})
	@ApiResponses({ 
		@ApiResponse(code = HttpStatus.SC_OK, message = "操作成功", response = AuthzStaffVo.class)
	})
	@BusinessLog(module = Constants.AUTHZ_STAFF, business = "查询员工信息", opt = BusinessType.SELECT)
	@GetMapping("detail/{id}")
	@RequiresPermissions("authz-staff:detail")
	@ResponseBody
	public Object detail(@PathVariable("id") String id) throws Exception { 
		AuthzStaffModel model = getAuthzStaffService().getModel(id);
		if( model == null) {
			return ApiRestResponse.empty(getMessage("authz.staff.get.empty"));
		}
		return getBeanMapper().map(model, AuthzStaffVo.class);
	}

	public IAuthzStaffService getAuthzStaffService() {
		return authzStaffService;
	}

	public void setAuthzStaffService(IAuthzStaffService authzStaffService) {
		this.authzStaffService = authzStaffService;
	}

}
