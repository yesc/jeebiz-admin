/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.setup.aspect;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.spring.boot.utils.Log4jUtils;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import net.jeebiz.admin.extras.logbiz.setup.Constants;
import net.jeebiz.boot.api.annotation.BusinessLog;
import net.jeebiz.boot.api.annotation.BusinessType;


@Aspect
@Component
public class BusinessLog4j2Aspect {
	
    /** 
     * 定义拦截规则：拦截有@RequestMapping注解的方法。 
     */  
    public void controllerAspect(BusinessLog bizLog){}  
    
    /*<aop:config>
		<aop:pointcut id="logPointcut"  expression="execution(* com.zfsoft..controller.*.*(..)) and @annotation(com.zfsoft.api.annotation.BusinessLog) and @annotation(businessLog)" />
		<aop:aspect id="logAspect" ref="businessLog4j2Aspect">
			<aop:after-returning method="afterReturing"  pointcut-ref="logPointcut" />
		</aop:aspect>
	</aop:config>*/

    
	/**
	 * 代理对象正常调用返回后advice
	 * @param jp
	 * @throws IOException 
	 * @throws TemplateException 
	 */
    @AfterReturning(pointcut = "execution(* net.jeebiz..mvc..**..*.*(..)) and @annotation(net.jeebiz.boot.api.annotation.BusinessLog) and @annotation(bizLog)")
	public void afterReturing(JoinPoint jp, BusinessLog bizLog) throws IOException{
		
    	Signature signature = jp.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		
		String[] argNames = methodSignature.getParameterNames();
		Object[] args = jp.getArgs();
		Map<String,Object> data = new HashMap<String,Object>();
		
		for (int i = 0 , j = argNames.length ; i < j ; i++){
			data.put(argNames[i], args[i]);
		}
		
		ThreadContext.put("module", bizLog.module());
		ThreadContext.put("biz", bizLog.business());
		ThreadContext.put("opt", bizLog.opt().getKey());
		
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		ThreadContext.put("userId", principal.getUserid());
		
		// 记录请求日志
		Log4jUtils.instance("Biz-Log").info(Constants.bizLogMarker, "业务操作成功.");
		
	}
    
    @AfterThrowing(throwing="ex", pointcut = "execution(* net.jeebiz..mvc..**..*.*(..)) and @annotation(net.jeebiz.boot.api.annotation.BusinessLog)  and @annotation(bizLog)")
	public void afterThrowing(Throwable ex, BusinessLog bizLog) throws IOException{
    	
    	ThreadContext.put("module", bizLog.module());
		ThreadContext.put("biz", bizLog.business());
		ThreadContext.put("opt", bizLog.opt().getKey());
		// 登录报错,这里还拿不到ShiroPrincipal对象
		if(bizLog.opt().equals(BusinessType.LOGIN)) {
			ServletRequest request = SubjectUtils.getWebSubject().getServletRequest();
			ThreadContext.put("userId", request.getParameter("username"));
		} else {
			ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
			ThreadContext.put("userId", principal.getUserid());
		}
		
		ThreadContext.put("clazz", ex.getClass().getName());
		
		// 记录请求日志
		Log4jUtils.instance("Biz-Excp").error(Constants.bizExcpMarker, "业务操作异常：" + ex.getMessage(), ex);
		
		
	}

}


