package com.macro.mall.portal.domain2;

import java.math.BigDecimal;
import java.util.Date;

import com.macro.mall.portal.util2.SignatureUtil;

/*************************************************

Desc: Mongo实现的购物车，用于代替OmsCartItem，除删掉与数据库有关的类似于Id、deleteStatus等字段外，功能字段完全一样

Author: ng123123

Date: 2020/10/6

QQ交流群: 892951935

**************************************************/ 

public class MemberCartItem {

	private Long memberId;
	
    private Long productId;

    private Long productSkuId;

    // "购买数量"
    private Integer quantity;

    // "添加到购物车的价格"
    private BigDecimal price;

    // "商品主图"
    private String productPic;

    // "商品名称"
    private String productName;

    // "商品副标题（卖点）"
    private String productSubTitle;

    // "商品sku条码"
    private String productSkuCode;

    // "会员昵称"
    private String memberNickname;

    // "创建时间"
    private Date createDate;

    // "修改时间"
    private Date modifyDate;

   // "商品分类"
    private Long productCategoryId;

    private String productBrand;

    private String productSn;

    // "商品销售属性:[{'key':'颜色','value':'颜色'},{'key':'容量','value':'4G'}]"
    private String productAttr;
	
	// 信息加密签名（用于确保用户端购物车数据未经篡改）
	private String signature;
	

	public String getKey() {
		String productKey = String.valueOf(productId);
		if ( productSkuId!=null && productSkuId>0 )
			productKey += "." + String.valueOf(productSkuId);
		return productKey;
	}
	
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSubTitle() {
        return productSubTitle;
    }

    public void setProductSubTitle(String productSubTitle) {
        this.productSubTitle = productSubTitle;
    }

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

    public String getMemberNickname() {
        return memberNickname;
    }

    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public String getProductAttr() {
        return productAttr;
    }

    public void setProductAttr(String productAttr) {
        this.productAttr = productAttr;
    }
	
	public String getSignature() {
		return signature;
	}
	
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	// 购物车信息验证客户端信息未篡改功能
	// 验证购物车商品签名（该签名从服务器返回的product列表中获取，由productId、productSkuId、price共同构成签名，price是productSkuId所指定sku的price）
	public boolean checkSignature() {
		String sign = SignatureUtil.productSkuSignature(productId, productSkuId, price);
		if ( sign.equals(signature) )
			return true;
		return false;
	}
}