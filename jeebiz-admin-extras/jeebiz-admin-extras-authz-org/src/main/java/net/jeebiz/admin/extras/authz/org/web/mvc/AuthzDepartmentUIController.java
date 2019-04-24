package net.jeebiz.admin.extras.authz.org.web.mvc;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import net.jeebiz.admin.extras.authz.org.service.IAuthzCompanyService;
import net.jeebiz.admin.extras.authz.org.service.IAuthzDepartmentService;
import net.jeebiz.boot.api.webmvc.BaseMapperController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "部门管理：UI跳转")
@Controller
@RequestMapping("/authz/dept/ui/")
public class AuthzDepartmentUIController extends BaseMapperController {

	@Autowired
	private IAuthzCompanyService authzCompanyService;
	@Autowired
	private IAuthzDepartmentService authzDepartmentService;
	
	@ApiIgnore
	@GetMapping("list")
	@RequiresPermissions("dept:list")
	public String list(@ApiIgnore Model uiModel) {
		uiModel.addAttribute("companys", getAuthzCompanyService().getPairValues(""));
		return "html/mhk/authz/dept/list";
	}
	
	@ApiIgnore
	@GetMapping("new")
	@RequiresPermissions("dept:new")
	public String newRule(@ApiIgnore Model uiModel) {
		uiModel.addAttribute("companys", getAuthzCompanyService().getPairValues(""));
		return "html/mhk/authz/dept/new";
	}
    
	@ApiIgnore
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "组卷策略ID", dataType = "String")
	})
	@GetMapping("renew/{id}")
	@RequiresPermissions("dept:renew")
	public String renewRule(@PathVariable("id") String id, @ApiIgnore Model uiModel) {
		uiModel.addAttribute("companys", getAuthzCompanyService().getPairValues(""));
		uiModel.addAttribute("model", getAuthzDepartmentService().getModel(id));
		return "html/mhk/authz/dept/renew";
	}
	
	@ApiIgnore
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "组卷策略ID", dataType = "String")
	})
	@GetMapping("detail/{id}")
	@RequiresPermissions("dept:detail")
	public String detail(@PathVariable("id") String id, @ApiIgnore Model uiModel) {
		uiModel.addAttribute("model", getAuthzDepartmentService().getModel(id));
		return "html/mhk/authz/dept/detail";
	}

	public IAuthzCompanyService getAuthzCompanyService() {
		return authzCompanyService;
	}

	public IAuthzDepartmentService getAuthzDepartmentService() {
		return authzDepartmentService;
	}

}
