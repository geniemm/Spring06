package com.care.root.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
	@GetMapping("test/index")
	public void test(HttpServletResponse res, HttpServletRequest req) throws IOException{
		System.out.println(req.getContextPath());  
		String msg = 
	               "<script>alert('aaaaaaaaa');";
	         msg += "location.href='/root/member/login';</script>";
	         res.setContentType("text/html; charset=utf-8");
	         //이 메시지가 사용자에게 보내졌을때의 파일타입을 지정하는것
	         PrintWriter out = res.getWriter();
	         out.print(msg);
	}
}
