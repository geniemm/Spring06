package com.care.root.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.care.root.common.LoginSession;

public class MemberInterceptor extends HandlerInterceptorAdapter{

	// servlet-context���� interceptor ����ؾ��� 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if(session.getAttribute(LoginSession.LOGIN) == null) {
	         //response.sendRedirect("login");
			// �α����� �Ǿ����� �ʴٸ�
	         String msg = 
	               "<script>alert('�α��� ���� �ϼ���');";
	         msg += "location.href='/root/member/login';</script>";
	         response.setContentType("text/html; charset=utf-8");
	         //�� �޽����� ����ڿ��� ������������ ����Ÿ���� �����ϴ°�
	         PrintWriter out = response.getWriter();
	         out.print(msg); //jsp�� print.out������ 
	         return false;
	      }
		System.out.println("list ��Ʈ�ѷ� �� ����-----");
		return true;
	}


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("-------list ��Ʈ�ѷ� �� ����");
	}

}
