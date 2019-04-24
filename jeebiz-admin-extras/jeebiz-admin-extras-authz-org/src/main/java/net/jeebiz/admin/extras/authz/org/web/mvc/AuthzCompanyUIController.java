package net.jeebiz.admin.extras.authz.org.web.mvc;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import net.jeebiz.admin.extras.authz.org.service.IAuthzCompanyService;
import net.jeebiz.boot.api.webmvc.BaseMapperController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "单位信息：UI跳转")
@Controller
@RequestMapping("/authz/company/ui/")
public class AuthzCompanyUIController extends BaseMapperController {
	
	@Autowired
	private IAuthzCompanyService authzCompanyService;
	
	@ApiIgnore
	@GetMapping("list")
	@RequiresPermissions("company:list")
	public String list(@ApiIgnore Model uiModel) {
		return "html/mhk/authz/company/list";
	}
	
	@ApiIgnore
	@GetMapping("new")
	@RequiresPermissions("company:new")
	public String newCompany(@ApiIgnore Model uiModel) {
		uiModel.addAttribute("companys", getAuthzCompanyService().getPairValues(""));
		return "html/mhk/authz/company/new";
	}
	
	@ApiIgnore
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "父级单位ID", dataType = "String")
	})
	@GetMapping("child")
	@RequiresPermissions("company:new")
	public String child(@RequestParam String id, @ApiIgnore Model uiModel) {
		uiModel.addAttribute("parent", id);
		uiModel.addAttribute("companys", getAuthzCompanyService().getPairValues(""));
		return "html/mhk/authz/company/child";
	}
    
	@ApiIgnore
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "单位ID", dataType = "String")
	})
	@GetMapping("renew/{id}")
	@RequiresPermissions("company:renew")
	public String renew(@PathVariable("id") String id, @ApiIgnore Model uiModel) {
		uiModel.addAttribute("companys", getAuthzCompanyService().getPairValues(id));
		uiModel.addAttribute("model", getAuthzCompanyService().getModel(id));
		return "html/mhk/authz/company/renew";
	}
	
	@ApiIgnore
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "组卷策略ID", dataType = "String")
	})
	@GetMapping("detail/{id}")
	@RequiresPermissions("company:detail")
	public String detail(@PathVariable("id") String id, @ApiIgnore Model uiModel) {
		uiModel.addAttribute("companys", getAuthzCompanyService().getPairValues(""));
		uiModel.addAttribute("model", getAuthzCompanyService().getModel(id));
		return "html/mhk/authz/company/detail";
	}

	public IAuthzCompanyService getAuthzCompanyService() {
		return authzCompanyService;
	}

}
