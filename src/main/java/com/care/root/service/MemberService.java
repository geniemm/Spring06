package com.care.root.service;

import java.util.List;
import java.util.Map;

import com.care.root.dto.MemberDTO;

public interface MemberService {

	public int logChk(String id,String pw);
	public List<MemberDTO> getList();
	//list는 arrayList의 부모형식 > arraylist로 결과값  처리 가능
	public int register(MemberDTO dto, String[] addr);
	public Map<String, Object> getMember(String id); //hashmap이 상속받는게 maps
	public void keepLogin(String sessionId, String id);
	public MemberDTO getUserSessionId(String sessionId);
}
