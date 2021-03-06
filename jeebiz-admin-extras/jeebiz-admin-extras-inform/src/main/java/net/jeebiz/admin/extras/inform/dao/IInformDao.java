/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.extras.inform.dao.entities.InformModel;
import net.jeebiz.boot.api.dao.BaseDao;

@Mapper
public interface IInformDao extends BaseDao<InformModel> {

	/**
	 * 消息通知统计信息
	 * @return  {
	 * 	 notice 		: 全部通知信息总数,
	 * 	 unreadNotice 	: 未读通知信息总数,
	 *   direct			: 全部私信信息总数,
	 *   unreadDirect	: 未读私信信息总数
	 * }
	 */
	Map<String, String> getStats(@Param("userId") String userId);
	
	/**
	 * 删除用户的通知信息
	 * @param userid
	 * @param ids
	 * @return
	 */
	int delInforms(@Param("userId") String userId,@Param("ids") List<String> ids);
	
}
