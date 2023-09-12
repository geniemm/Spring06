package com.care.root.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.care.root.common.LoginSession;

public class TestIn extends HandlerInterceptorAdapter implements LoginSession{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("새로운 인터셉터 등록");
		HttpSession session = request.getSession();
		// session.getAttribute(Login) == null; 아래코드는 이 코드와 결과적으로 같다 
		if(StringUtils.isEmpty(session.getAttribute(LOGIN))) {
			  String msg = "<script>alert('로그인 먼저 하세요');";
		         msg += "location.href='/root/member/login';</script>";
		         response.setContentType("text/html; charset=utf-8");
		         PrintWriter out = response.getWriter();
		         out.print(msg);
		         return false;
		}
		return true;
	}

}
