package com.care.root.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.care.root.common.LoginSession;
import com.care.root.dto.MemberDTO;
import com.care.root.service.MemberService;

public class AutoLoginInterceptor extends HandlerInterceptorAdapter implements LoginSession	{

	@Autowired MemberService ms;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Cookie loginCookie = WebUtils.getCookie(request,"loginCookie"); // request.getCookie랑같다
		if( loginCookie != null ) {
			// 쿠키값이 존재하면
			MemberDTO dto = ms.getUserSessionId(loginCookie.getValue());
			if( dto != null ) {
				request.getSession().setAttribute(LOGIN, dto.getId());
				
			}
		}
		
		
		return true;
	}
	
}
