package com.egen.texasBurger.interceptors;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.egen.texasBurger.model.ApiMetadata;
import com.egen.texasBurger.repository.ApiTimeRepository;

@Component
@Log4j2
public class ExecutionTimeInterceptor implements HandlerInterceptor  {
	
	@Autowired
	ApiTimeRepository apirepo;
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception arg3)
			throws Exception {
		
		long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        ApiMetadata apiTime = new ApiMetadata();
        apiTime.setMethodType(request.getMethod());
        apiTime.setUri(request.getRequestURI());
        apiTime.setDate(LocalDate.now());
        apiTime.setExecutionTime(endTime - startTime);
        apirepo.save(apiTime);
	}


	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object object) throws Exception {
		
		long startTime = System.currentTimeMillis();
	    request.setAttribute("startTime", startTime);
		return true;
	}

}