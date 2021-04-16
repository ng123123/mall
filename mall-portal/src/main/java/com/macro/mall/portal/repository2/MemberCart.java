package com.macro.mall.portal.repository2;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import com.macro.mall.portal.domain2.MemberCartItem;

/*************************************************

Desc: Mongo实现的购物车

Author: ng123123

Date: 2020/10/6

QQ交流群: 892951935

**************************************************/ 

@Document
public class MemberCart {
	
	@Id
	private String id;
	
	@Indexed(unique=true)
    private Long memberId;
	
	private Map<String, MemberCartItem> memberCartItemMap;

	public MemberCart(Long memberId) {
		setMemberId(memberId);
		this.memberCartItemMap = new HashMap<String, MemberCartItem>();
	}
	
	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
	
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
	
	public Map<String, MemberCartItem> getMemberCartItemMap() {
		return memberCartItemMap;
	}
	
	public void setMemberCartItemMap(Map<String, MemberCartItem> memberCartItemMap) {
		this.memberCartItemMap = memberCartItemMap;
	}
}