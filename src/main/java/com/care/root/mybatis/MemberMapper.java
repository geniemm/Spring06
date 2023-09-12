package com.care.root.mybatis;

import java.util.List;
import java.util.Map;

import com.care.root.dto.MemberDTO;

public interface MemberMapper {
	public MemberDTO getMember(String id);
	public List<MemberDTO> getList();
	public int register(MemberDTO dto);
	public void keepLogin(Map<String, Object> map);
	public MemberDTO getUserSessionId(String sessionId);
}
