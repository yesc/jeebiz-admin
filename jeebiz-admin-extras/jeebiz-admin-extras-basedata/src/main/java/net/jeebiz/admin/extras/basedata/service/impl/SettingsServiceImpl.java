package net.jeebiz.admin.extras.basedata.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.basedata.dao.ISettingsDao;
import net.jeebiz.admin.extras.basedata.dao.entities.SettingsModel;
import net.jeebiz.admin.extras.basedata.service.ISettingsService;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class SettingsServiceImpl extends BaseServiceImpl<SettingsModel, ISettingsDao> implements ISettingsService {

	@Override
	public List<PairModel> getPairValues(String gkey) {
		return getDao().getPairValues(gkey);
	}
	
}
