/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.basedata.web.mvc;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.biz.utils.StringUtils;
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
import net.jeebiz.admin.extras.basedata.dao.entities.KeyValueModel;
import net.jeebiz.admin.extras.basedata.service.IKeyGroupService;
import net.jeebiz.admin.extras.basedata.service.IKeyValueService;
import net.jeebiz.admin.extras.basedata.setup.Constants;
import net.jeebiz.admin.extras.basedata.web.vo.KeyValuePaginationVo;
import net.jeebiz.admin.extras.basedata.web.vo.KeyValueRequestVo;
import net.jeebiz.admin.extras.basedata.web.vo.KeyValueVo;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.webmvc.BaseMapperController;
import net.jeebiz.boot.api.webmvc.Result;

@Api(tags = "基础数据：键值对形式的数据维护")
@RestController
@RequestMapping("/extras/basedata/keyvalue/")
public class KeyValueController extends BaseMapperController {
	
	@Autowired
	private IKeyGroupService keyGroupService;
	@Autowired
	private IKeyValueService keyValueService;
	
	
	@ApiOperation(value = "keyvalue:list", notes = "分页查询基础数据")
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "body", name = "paginationVo", value = "用户信息筛选条件", dataType = "AuthzUserPaginationVo")
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "根据分组分页查询基础数据", opt = BusinessType.SELECT)
	@PostMapping("list")
	@RequiresPermissions("keyvalue:list")
	@ResponseBody
	public Object list(@Valid @RequestBody KeyValuePaginationVo paginationVo){
		
		KeyValueModel model =  getBeanMapper().map(paginationVo, KeyValueModel.class);
		Page<KeyValueModel> pageResult = getKeyValueService().getPagedList(model);
		List<KeyValueVo> retList = Lists.newArrayList();
		for (KeyValueModel keyvalueModel : pageResult.getRecords()) {
			retList.add(getBeanMapper().map(keyvalueModel, KeyValueVo.class));
		}
		
		return new Result<KeyValueVo>(pageResult, retList);
		
	}
	
	@ApiOperation(value = "查询基础数据分组", notes = "查询基础数据分组")
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "查询基础数据分组", opt = BusinessType.SELECT)
	@GetMapping("groups")
	@RequiresPermissions("keyvalue:list")
	@ResponseBody
	public Object groups() throws Exception {
		return getKeyGroupService().getPairValues();
	}
	
	@ApiOperation(value = "根据分组查询基础数据", notes = "根据分组查询基础数据")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "gkey", value = "基础数据分组", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "根据分组查询基础数据", opt = BusinessType.SELECT)
	@GetMapping("pairs")
	@RequiresPermissions("keyvalue:list")
	@ResponseBody
	public Object pairs(@RequestParam String gkey) throws Exception {
		return getKeyValueService().getPairValues(gkey);
	}
	
	@ApiOperation(value = "创建基础数据", notes = "增加一个新的基础数据")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "vo", value = "基础数据传输对象", dataType = "KeyValueVo") 
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "创建基础数据", opt = BusinessType.INSERT)
	@PostMapping("new")
	@RequiresPermissions("keyvalue:new")
	@ResponseBody
	public Object keyvalue(@Valid @RequestBody KeyValueVo keyvalueVo) throws Exception {
		KeyValueModel model = getBeanMapper().map(keyvalueVo, KeyValueModel.class);
		
		int ct = getKeyValueService().getCount(model);
		if(ct > 0) {
			return fail("keyvalue.new.conflict");
		}
		// 新增一条数据库配置记录
		int result = getKeyValueService().insert(model);
		if(result == 1) {
			return success("keyvalue.new.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("keyvalue.new.fail", result);
	}
	
	@ApiOperation(value = "更新基础数据", notes = "更新基础数据")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "keyvalueVo", value = "基础数据", required = true, dataType = "KeyValueVo"),
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "更新基础数据", opt = BusinessType.UPDATE)
	@PostMapping("update")
	@RequiresPermissions("keyvalue:update")
	@ResponseBody
	public Object update(@Valid @RequestBody KeyValueVo keyvalueVo) throws Exception {
		KeyValueModel model = getBeanMapper().map(keyvalueVo, KeyValueModel.class);
		int ct = getKeyValueService().getCount(model);
		if(ct > 0) {
			return fail("keyvalue.update.conflict");
		}
		int result = getKeyValueService().update(model);
		if(result == 1) {
			return success("keyvalue.update.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("keyvalue.update.fail", result);
	}
	
	@ApiOperation(value = "更新基础数据状态", notes = "更新基础数据状态")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", required = true, value = "基础数据ID", dataType = "String"),
		@ApiImplicitParam(name = "status", required = true, value = "基础数据状态", dataType = "String", allowableValues = "1,0")
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "更新基础数据状态", opt = BusinessType.UPDATE)
	@PostMapping("status")
	@RequiresPermissions(value = {"keyvalue:enable", "keyvalue:disable"}, logical = Logical.OR)
	@ResponseBody
	public Object status(@RequestParam String id, @RequestParam String status) throws Exception {
		int result = getKeyValueService().setStatus(id, status);
		if(result == 1) {
			return success("keyvalue.status.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("keyvalue.status.fail", result);
	}
	
	@ApiOperation(value = "删除基础数据", notes = "删除基础数据")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "ids", value = "基础数据ID,多个用,拼接", required = true, dataType = "String")
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "删除基础数据", opt = BusinessType.UPDATE)
	@PostMapping("delete")
	@RequiresPermissions("keyvalue:delete")
	@ResponseBody
	public Object delete(@RequestParam String ids) throws Exception {
		// 执行基础数据删除操作
		List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
		int result = getKeyValueService().batchDelete(idList);
		if(result > 0) {
			return success("keyvalue.delete.success", result);
		}
		// 逻辑代码，如果发生异常将不会被执行
		return fail("keyvalue.delete.fail", result);
	}
	
	@ApiOperation(value = "批量更新分组内的基础数据", notes = "批量更新分组内的基础数据")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "requestVo", value = "基础数据集合", required = true, dataType = "KeyValueRequestVo"),
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "批量更新分组内的基础数据", opt = BusinessType.UPDATE)
	@PostMapping(value = "batch/update")
	@RequiresPermissions("keyvalue:update")
	@ResponseBody
	public Object batchUpdate(@Valid @RequestBody KeyValueRequestVo requestVo) throws Exception {
		
		try {
			
			List<KeyValueModel> list = Lists.newArrayList();
			for (KeyValueVo keyvalueVo : requestVo.getDatas()) {
				KeyValueModel model = getBeanMapper().map(keyvalueVo, KeyValueModel.class);
				model.setGkey(requestVo.getGkey());
				list.add(model);
			}
			// 批量执行基础数据更新操作
			getKeyValueService().batchUpdate(list);
			return success("keyvalue.update.success");
		} catch (Exception e) {
			return fail("keyvalue.update.fail");
		}
	}
	
	@ApiOperation(value = "查询基础数据信息", notes = "根据ID查询基础数据信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam( name = "id", required = true, value = "基础数据ID", dataType = "String")
	})
	@BusinessLog(module = Constants.EXTRAS_BASEDATA, business = "查询基础数据信息", opt = BusinessType.SELECT)
	@GetMapping("detail/{id}")
	@RequiresPermissions(value = {"keyvalue:list" ,"keyvalue:detail" }, logical = Logical.OR)
	@ResponseBody
	public Object detail(@PathVariable("id") String id) throws Exception { 
		return getKeyValueService().getModel(id);
	}

	public IKeyGroupService getKeyGroupService() {
		return keyGroupService;
	}

	public IKeyValueService getKeyValueService() {
		return keyValueService;
	}
	
}
