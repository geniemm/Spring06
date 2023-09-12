package com.care.root.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.care.root.common.LoginSession;
import com.care.root.dto.MemberDTO;
import com.care.root.service.MemberService;


@Controller
@RequestMapping("member")
public class MemberController implements LoginSession{
	
	@Autowired MemberService ms;
	
	@GetMapping("login")
	public String login() {
		return "member/login";
	}
	@PostMapping("logChk")
	public String logChk(@RequestParam String id,
						@RequestParam String pw, 
						@RequestParam(required = false, defaultValue="off") String autoLogin,
						HttpSession session,
						RedirectAttributes rs ) {
		//RedirectAttributes �ش��ϴ� ��η� ���� �����Ҷ� ����Ѵ�
		int result= ms.logChk(id, pw);
		
		//autoLogin ����ڰ� üũ�ϸ� on, ���ϸ� off required = false >>�̰� ���� �ȵ����� null�� �ְڴ�
		
		//�α��� ����
		if(result==0) {
			//session.setAttribute(LOGIN, id);
			// LoginSession�������̽����ִ� login�̶�� ������ ����ϰڴ� >> LOGIN�� ���� login�̹Ƿ� �����̸��� "login" ����
			// (�������Ϸ� ����ϴ°� �����ϱ⿡ ���ϴ�)
			// implements�� ��ӹ޾����� LoginSession�� ���� ���ʿ䰡 ������
			
			rs.addAttribute("id", id ); // id�� �Ѱ�����
		
			rs.addAttribute("autoLogin", autoLogin ); // id�� �Ѱ�����
			
			return "redirect:successLogin";
		}
		return "redirect:login";
	}
	@GetMapping("successLogin") // �����ʿ��ѰǾƴѵ� �ѹ� ������~
	public String successLogin(@RequestParam String id, 
								@RequestParam String autoLogin,
								HttpSession session,
								HttpServletResponse res) {
		System.out.println("autoLogin: "+autoLogin);
		if(autoLogin.equals("on")) {
			int limitTime = 60*60*24*90; //90�� ���� (��,��,��,��)
			Cookie loginCookie = new Cookie("loginCookie",session.getId());
			loginCookie.setPath("/");
			loginCookie.setMaxAge(limitTime);
			res.addCookie(loginCookie);
			
			ms.keepLogin(session.getId(),id);
		}
		
		session.setAttribute(LOGIN, id);
		return "member/successLogin";
	}
	@GetMapping("logout")
	public String logout(HttpSession session, //@CookieValue(required = false) Cookie loginCookie) {
		@CookieValue(value="loginCookie",required = false) Cookie cookie, HttpServletResponse res) {
		if(cookie != null) {
			cookie.setMaxAge(0);
			res.addCookie(cookie);
			ms.keepLogin("nan", (String)session.getAttribute(LOGIN));
		}
		session.invalidate();
		return "redirect:/index";
	}
	
	
	@GetMapping("list")
	public String list ( Model model ) {
		System.out.println("��Ʈ�ѷ� ����Ʈ ����");
		model.addAttribute("list", ms.getList());
		return "member/list";
		
	}
	
	
	@GetMapping("register_view")
	public String regView() {
		return "member/register_view";
	}
	
	
	@PostMapping("register")
	public String register(HttpServletRequest req, MemberDTO dto) {
		String[] addr = req.getParameterValues("addr");
		// jsp���� addr�� ���� ��� �����ؼ� �迭�ι޾ƿͼ� �ϳ��� ó���ϴ� ���
		// �������� �ϳ��� ���ļ� ��������
		String ad = "";
		for(String a :addr) {
			System.out.println(a);
			ad+= a+"/"; 
		}
		System.out.println("============");
		// �÷��� �ִ������� ���߿� ���� ������ �ؾ��Ҷ� / �� �������� �������־
		System.out.println(ad);
		String[] addr02= ad.split("/");
		for(String a1 : addr02) {
			System.out.println(a1);
		}
		System.out.println("==== dto ====");
		System.out.println(dto.getId());
		System.out.println(dto.getPw());
		
		ms.register(dto,req.getParameterValues("addr"));
		
		return "redirect:login";
		
	}
	@GetMapping("info")
	public String info(@RequestParam String id, Model model) {
		model.addAttribute("mem", ms.getMember(id));

		return "member/info";
	}
}




