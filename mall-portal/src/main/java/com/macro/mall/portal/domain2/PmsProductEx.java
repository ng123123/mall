package com.macro.mall.portal.domain2;

import com.macro.mall.model.PmsProduct;

/*************************************************

Desc: 购物车中选择规格的商品信息

Author: ng123123

Date: 2020/10/20

QQ交流群: 892951935

**************************************************/ 

public class PmsProductEx extends PmsProduct {
    private String signature;
	
	public String getSignature() {
		return signature;
	}
	
	public void setSignature(String signature) {
		this.signature = signature;
	}
}
