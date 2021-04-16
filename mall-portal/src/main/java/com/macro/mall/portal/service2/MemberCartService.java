package com.macro.mall.portal.service2;

import com.macro.mall.portal.domain2.MemberCartItem;
import com.macro.mall.portal.repository2.MemberCart;
import com.macro.mall.portal.domain.CartProduct;
//import com.macro.mall.portal.domain.CartPromotionItem;

import java.util.List;
import java.util.Map;

// cnr
import com.macro.mall.portal.domain2.MemberCartPromotionItem;

/*************************************************

Desc: 购物车管理Service

Author: ng123123

Date: 2020/10/7

QQ交流群: 892951935

**************************************************/ 

public interface MemberCartService {
    /**
     * 根据接收的商品列表更新购物车中的商品，有则增加数量，无则添加到购物车
     */
    Map<String, MemberCartItem> update(List<MemberCartItem> cartItems);

    /**
     * 根据会员编号获取购物车列表
     */
    Map<String, MemberCartItem> list(Long memberId);

    /**
     * 获取包含促销活动信息的购物车列表
     */
    List<MemberCartPromotionItem> listPromotionByItemKeys(Long memberId, List<String> cartItemKeys);
	
    // 获取包含促销活动信息的购物车列表（未登录用户下单，在获取促销信息时）
    List<MemberCartPromotionItem> listPromotionByItems(List<MemberCartItem> cartItems);

    /**
     * 修改某个购物车商品的数量
     */
    //int updateQuantity(Long id, Long memberId, Integer quantity);

    /**
     * 批量删除购物车中的商品
     */
    void delete(Long memberId,List<String> cartItemKeys);

    /**
     *获取购物车中用于选择商品规格的商品信息
     */
    CartProduct getCartProduct(Long productId);

    /**
     * 修改购物车中商品的规格
     */
    int updateAttr(MemberCartItem cartItem);

    /**
     * 清空购物车
     */
    int clear(Long memberId);
}
