/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.web.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.StringUtils;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.jeebiz.admin.extras.inform.dao.entities.InformModel;
import net.jeebiz.admin.extras.inform.service.IInformService;
import net.jeebiz.admin.extras.inform.setup.Constants;
import net.jeebiz.admin.extras.inform.web.vo.InformPaginationVo;
import net.jeebiz.admin.extras.inform.web.vo.InformVo;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.webmvc.BaseMapperController;
import net.jeebiz.boot.api.webmvc.Result;

@Api(tags = "消息通知管理")
@Controller
@RequestMapping("/extras/inform/")
public class InformController extends BaseMapperController {
	
	@Autowired
	private IInformService informService;

	@ApiOperation(value = "待处理通知总数", notes = "查询待处理通知总数")
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "查询待处理通知总数", opt = BusinessType.SELECT)
	@PostMapping(value = "pending")
	@RequiresAuthentication
	@ResponseBody
	public Object pending() throws Exception {
		
		InformModel model = new InformModel();
		model.setUserId(SubjectUtils.getPrincipal(ShiroPrincipal.class).getUserid());
		
		return getInformService().getCount(model);
	}
	
	@ApiOperation(value = "查询服务消息通知", notes = "分页查询服务消息通知")
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "分页查询服务消息通知", opt = BusinessType.SELECT)
	@PostMapping(value = "list")
	@RequiresAuthentication
	@ResponseBody
	public Object list(@Valid InformPaginationVo paginationVo)
			throws Exception {
		
		InformModel model = getBeanMapper().map(paginationVo, InformModel.class);
		model.setUserId(SubjectUtils.getPrincipal(ShiroPrincipal.class).getUserid());
		
		Page<InformModel> pageResult = getInformService().getPagedList(model);
		List<InformVo> retList = new ArrayList<InformVo>();
		for (InformModel registryModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(registryModel, InformVo.class));
		}
		
		return new Result<InformVo>(pageResult, retList);
		
	}

	@ApiOperation(value = "消息通知统计信息", notes = "查询消息通知统计信息")
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "查询消息通知统计信息", opt = BusinessType.SELECT)
	@PostMapping(value = "stats")
	@RequiresAuthentication
	@ResponseBody
	public Object stats() throws Exception {
		return getInformService().getStats(SubjectUtils.getPrincipal(ShiroPrincipal.class).getUserid());
	}
	
	@ApiOperation(value = "消息通知信息", notes = "查询指定ID的消息通知信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "id", value = "服务消息通知ID", required = true, dataType = "String"),
	})
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "查询指定ID的消息通知信息", opt = BusinessType.SELECT)
	@PostMapping(value = "detail")
	@RequiresAuthentication
	@ResponseBody
	public Object detail(@RequestParam String id) throws Exception {
		return getInformService().getModel(id);
	}
	
	@ApiOperation(value = "删除消息通知", notes = "删除消息通知")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "ids", value = "消息通知ID,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "删除消息通知", opt = BusinessType.UPDATE)
	@PostMapping("delete")
	@RequiresAuthentication
	@ResponseBody
	public Object delete(@RequestParam String ids) throws Exception {
		// 执行消息通知删除操作
		String userId = SubjectUtils.getPrincipal(ShiroPrincipal.class).getUserid();
		int result = getInformService().delInforms(userId, Lists.newArrayList(StringUtils.tokenizeToStringArray(ids)));
		if(result > 0) {
			return success("inform.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return success("inform.delete.error", result);
	}
	
	@ApiOperation(value = "阅读消息通知", notes = "阅读消息通知")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "ids", value = "消息通知ID,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "阅读消息通知", opt = BusinessType.UPDATE)
	@PostMapping("read")
	@RequiresAuthentication
	@ResponseBody
	public Object read(@RequestParam String ids) throws Exception {
		
		InformModel model = new InformModel();
		model.setIds(Lists.newArrayList(StringUtils.tokenizeToStringArray(ids)));
		model.setUserId(SubjectUtils.getPrincipal(ShiroPrincipal.class).getUserid());
		model.setStatus("1");
		
		// 执行消息通知阅读操作
		int result = getInformService().update(model);
		if(result == 1) {
			return success("inform.read.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return success("inform.read.error", result);
	}
	

	@ApiOperation(value = "阅读消息通知", notes = "阅读消息通知")
	@BusinessLog(module = Constants.EXTRAS_INFORM, business = "阅读消息通知", opt = BusinessType.UPDATE)
	@PostMapping("readall")
	@RequiresAuthentication
	@ResponseBody
	public Object readall() throws Exception {
		
		InformModel model = new InformModel();
		model.setUserId(SubjectUtils.getPrincipal(ShiroPrincipal.class).getUserid());
		model.setStatus("1");
		
		// 执行消息通知阅读操作
		int result = getInformService().update(model);
		if(result == 1) {
			return success("inform.readall.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return success("inform.readall.error", result);
	}

	public IInformService getInformService() {
		return informService;
	}

	public void setInformService(IInformService informService) {
		this.informService = informService;
	}
	
}
