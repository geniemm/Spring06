package com.care.root.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.care.root.dto.MemberDTO;
import com.care.root.mybatis.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberMapper mm;

	BCryptPasswordEncoder encoder; // 비밀번호 암호화하는 기능

	public MemberServiceImpl() {
		encoder = new BCryptPasswordEncoder();
	}

	public int logChk(String id, String pw) {
		MemberDTO dto = mm.getMember(id);
		int result = 1;
		if (dto != null) {
			// dto에 값이 들어오면 아래코드 실행
			System.out.println(dto.getId());
			System.out.println(dto.getPw());
			System.out.println(dto.getAddr());
			if (encoder.matches(pw, dto.getPw()) || pw.equals(dto.getPw())) {
				result = 0;
			}
		}
		return result;
	}

	public void keepLogin(String sessionId, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sessionId", sessionId);
		map.put("id", id);
		mm.keepLogin(map);
	}

	public MemberDTO getUserSessionId(String sessionId) {
		
		return mm.getUserSessionId( sessionId );
	}

	public List<MemberDTO> getList() {
		List<MemberDTO> list = null;
		try {
			list = mm.getList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int register(MemberDTO dto, String[] addr) {
		String ad = "";
		for (String a : addr) {
			ad += a + "/";
		}
		dto.setAddr(ad); // 변경한(합쳐진) 주소 다시 넣어준다
		System.out.println("평문:" + dto.getPw());
		System.out.println("암호화:" + encoder.encode(dto.getPw()));

		dto.setPw(encoder.encode(dto.getPw()));
		int result = 0;
		try {
			result = 1;
			mm.register(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Map<String, Object> getMember(String id) {

		MemberDTO dto = mm.getMember(id);
		System.out.println(dto.getId());
		System.out.println(dto.getPw());
		System.out.println(dto.getAddr());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dto", dto);
		String[] addr = dto.getAddr().split("/");
		if (addr.length > 1) {
			map.put("addr1", addr[0]);
			map.put("addr2", addr[1]);
			map.put("addr3", addr[2]);
		}
		return map;
	}
}
