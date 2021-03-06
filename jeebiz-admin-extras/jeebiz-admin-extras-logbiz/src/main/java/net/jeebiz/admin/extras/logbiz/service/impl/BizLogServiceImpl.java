/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.logbiz.dao.IBizLogDao;
import net.jeebiz.admin.extras.logbiz.dao.entities.BizLogModel;
import net.jeebiz.admin.extras.logbiz.service.IBizLogService;
import net.jeebiz.boot.api.service.BaseMapperServiceImpl;

/**
 * 业务操作日志Service实现
 */
@Service
public class BizLogServiceImpl extends BaseMapperServiceImpl<BizLogModel, IBizLogDao>
		implements IBizLogService {

}
