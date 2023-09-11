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
		//RedirectAttributes 해당하는 경로로 값을 전달할때 사용한다
		int result= ms.logChk(id, pw);
		
		//로그인 성공
		if(result==0) {
			
			//session.setAttribute(LOGIN, id);
			// LoginSession인터페이스에있는 login이라는 변수를 사용하겠다 >> LOGIN의 값이 login이므로 세션이름은 "login" 생성
			// (공통파일로 사용하는게 협업하기에 편리하다)
			// implements로 상속받았으니 LoginSession을 따로 쓸필요가 없어짐
			
			rs.addAttribute("id", id ); // id값 넘겨주자
			return "redirect:successLogin";
		}
		return "redirect:login";
	}
	@GetMapping("successLogin") // 굳이필요한건아닌데 한번 만들어보자~
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
		// jsp에서 addr에 값을 모두 저장해서 배열로받아와서 하나씩 처리하는 방식
		// 세가지를 하나로 합쳐서 보내야함
		String ad = "";
		for(String a :addr) {
			System.out.println(a);
			ad+= a+"/"; 
		}
		System.out.println("============");
		// 컬럼을 넣는이유는 나중에 따로 수정을 해야할때 / 를 기준으로 나눌수있어서
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




