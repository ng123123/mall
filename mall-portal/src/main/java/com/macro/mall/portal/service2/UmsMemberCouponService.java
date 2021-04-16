package com.macro.mall.portal.service2;

import com.macro.mall.model.SmsCoupon;
import com.macro.mall.model.SmsCouponHistory;
//import com.macro.mall.portal.domain.CartPromotionItem;
import com.macro.mall.portal.domain.SmsCouponHistoryDetail;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// cnr
import com.macro.mall.portal.domain2.MemberCartPromotionItem;

/*************************************************

Desc: 用户优惠券管理Service

Author: ng123123

Date: 2020/10/13

QQ交流群: 892951935

**************************************************/ 

public interface UmsMemberCouponService {
    /**
     * 会员添加优惠券
     */
    @Transactional
    void add(Long couponId);

    /**
     * 获取优惠券历史列表
     */
    List<SmsCouponHistory> listHistory(Integer useStatus);

    /**
     * 根据购物车信息获取可用优惠券
     */
    List<SmsCouponHistoryDetail> listCart(List<MemberCartPromotionItem> cartItemList, Integer type);

    /**
     * 获取当前商品相关优惠券
     */
    List<SmsCoupon> listByProduct(Long productId);

    /**
     * 获取用户优惠券列表
     */
    List<SmsCoupon> list(Integer useStatus);
}
