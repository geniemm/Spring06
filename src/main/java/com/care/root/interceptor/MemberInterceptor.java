package com.care.root.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.care.root.common.LoginSession;

public class MemberInterceptor extends HandlerInterceptorAdapter{

	// servlet-context에서 interceptor 등록해야함 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if(session.getAttribute(LoginSession.LOGIN) == null) {
	         //response.sendRedirect("login");
			// 로그인이 되어있지 않다면
	         String msg = 
	               "<script>alert('로그인 먼저 하세요');";
	         msg += "location.href='/root/member/login';</script>";
	         response.setContentType("text/html; charset=utf-8");
	         //이 메시지가 사용자에게 보내졌을때의 파일타입을 지정하는것
	         PrintWriter out = response.getWriter();
	         out.print(msg); //jsp의 print.out과같은 
	         return false;
	      }
		System.out.println("list 컨트롤러 전 실행-----");
		return true;
	}


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("-------list 컨트롤러 후 실행");
	}

}
