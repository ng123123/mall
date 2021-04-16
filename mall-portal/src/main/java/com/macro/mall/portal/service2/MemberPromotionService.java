package com.macro.mall.portal.service2;

//import com.macro.mall.model.OmsCartItem;
//import com.macro.mall.portal.domain.CartPromotionItem;

import java.util.List;

// cnr
import com.macro.mall.portal.domain2.MemberCartItem;
import com.macro.mall.portal.domain2.MemberCartPromotionItem;

/*************************************************

Desc: 促销管理Service

Author: ng123123

Date: 2020/10/8

QQ交流群: 892951935

**************************************************/ 

public interface MemberPromotionService {
    /**
     * 计算购物车中的促销活动信息
     * @param cartItemList 购物车
     */
    List<MemberCartPromotionItem> calcCartPromotion(List<MemberCartItem> cartItemList);
}
