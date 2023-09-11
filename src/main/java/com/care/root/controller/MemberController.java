package com.care.root.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.care.root.common.LoginSession;
import com.care.root.dto.MemberDTO;
import com.care.root.service.MemberService;

import oracle.jdbc.proxy.annotation.Post;

@Controller
@RequestMapping("member")
public class MemberController implements LoginSession{
	
	@Autowired MemberService ms;
	
	@GetMapping("login")
	public String login() {
		return "member/login";
	}
	@PostMapping("logChk")
	public String logChk(@RequestParam String id,@RequestParam String pw
						, HttpSession session, RedirectAttributes rs) {
		//RedirectAttributes �ش��ϴ� ��η� ���� �����Ҷ� ����Ѵ�
		int result= ms.logChk(id, pw);
		
		//�α��� ����
		if(result==0) {
			
			//session.setAttribute(LOGIN, id);
			// LoginSession�������̽����ִ� login�̶�� ������ ����ϰڴ� >> LOGIN�� ���� login�̹Ƿ� �����̸��� "login" ����
			// (�������Ϸ� ����ϴ°� �����ϱ⿡ ���ϴ�)
			// implements�� ��ӹ޾����� LoginSession�� ���� ���ʿ䰡 ������
			
			rs.addAttribute("id", id ); // id�� �Ѱ�����
			return "redirect:successLogin";
		}
		return "redirect:login";
	}
	@GetMapping("successLogin") // �����ʿ��ѰǾƴѵ� �ѹ� ������~
	public String successLogin(@RequestParam String id, HttpSession session) {
		session.setAttribute(LOGIN, id);
		return "member/successLogin";
	}
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:login";
	}
	
	@GetMapping("list")
	public String list ( Model model ) {
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




