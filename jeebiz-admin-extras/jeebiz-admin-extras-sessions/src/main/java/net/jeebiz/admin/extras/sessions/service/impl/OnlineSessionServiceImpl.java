/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sessions.service.impl;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.biz.web.Constants;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleOnlineSession;
import org.apache.shiro.session.mgt.SimpleOnlineSession.OnlineStatus;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import net.jeebiz.admin.extras.sessions.dao.IOnlineSessionDao;
import net.jeebiz.admin.extras.sessions.dao.entities.OnlineSessionModel;
import net.jeebiz.admin.extras.sessions.service.IOnlineSessionService;
import net.jeebiz.admin.extras.sessions.web.vo.OnlineSessionVo;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class OnlineSessionServiceImpl extends BaseServiceImpl<OnlineSessionModel, IOnlineSessionDao>
		implements IOnlineSessionService {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	@Autowired  
    private SessionDAO sessionDao;

	@Override
	public List<OnlineSessionVo> getActiveSessions() {
		
		if(sessionDao == null){
			throw new IllegalStateException("sessionDao must be set for this filter");
		}
    	Collection<Session> sessions = getSessionDao().getActiveSessions();
    	List<OnlineSessionVo> onlineSessions = Lists.newArrayList();
    	for(Session session : sessions){         

			OnlineSessionVo sessionVo = new OnlineSessionVo(String.valueOf(session.getId()), session.getHost(),
					dateFormat.format(session.getStartTimestamp()), 
					dateFormat.format(session.getLastAccessTime()),
					session.getTimeout());
    		
    		if(session instanceof SimpleOnlineSession) {
    			SimpleOnlineSession onlineSession = (SimpleOnlineSession) session;
    			sessionVo.setStatus(onlineSession.getStatus().getInfo());
    			sessionVo.setUserAgent(onlineSession.getUserAgent());
    			sessionVo.setSystemHost(onlineSession.getSystemHost());
    		}
    		if(Boolean.TRUE.equals(session.getAttribute(Constants.SESSION_FORCE_LOGOUT_KEY))) {
    			sessionVo.setStatus(OnlineStatus.force_logout.getInfo());
    		} 
    		onlineSessions.add(sessionVo);
		}
		return onlineSessions;
	}

	@Override
	public boolean forceLogout(String sessionId) {
		try {  
            Session session = getSessionDao().readSession(sessionId);  
            if(session != null) {  
            	if(session instanceof SimpleOnlineSession) {
        			SimpleOnlineSession onlineSession = (SimpleOnlineSession) session;
        			onlineSession.setStatus(OnlineStatus.force_logout);
        		}
                session.setAttribute(Constants.SESSION_FORCE_LOGOUT_KEY, Boolean.TRUE);  
            }
            return true;
        } catch (Exception e) {/*ignore*/
        	return false;
        }  
	}  
	
	@Override
	public void offline(String sessionId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void online(OnlineSessionModel onlineSession) {
		// TODO Auto-generated method stub
		
	}
	
	public SessionDAO getSessionDao() {
		return sessionDao;
	}

	public void setSessionDao(SessionDAO sessionDao) {
		this.sessionDao = sessionDao;
	}
	
}
