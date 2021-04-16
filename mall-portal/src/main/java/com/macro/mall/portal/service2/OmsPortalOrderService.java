package com.macro.mall.portal.service2;

import com.macro.mall.common.api.CommonPage;
//import com.macro.mall.portal.domain.ConfirmOrderResult;
import com.macro.mall.portal.domain.OmsOrderDetail;
//import com.macro.mall.portal.domain.OrderParam;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

// cnr
import com.macro.mall.portal.domain2.OrderParam;
import com.macro.mall.portal.domain2.GuestOrderParam;
import com.macro.mall.portal.domain2.ConfirmOrderResult;
import com.macro.mall.portal.domain2.MemberCartItem;

/*************************************************

Desc: 前台订单管理Service

Author: ng123123

Date: 2020/10/11

QQ交流群: 892951935

**************************************************/ 

public interface OmsPortalOrderService {
    /**
     * 根据用户购物车信息生成确认单信息
     * @param cartIds
     */
    ConfirmOrderResult generateConfirmOrder(List<String> cartItemKeys);

    /**
     * 根据提交信息生成订单
     */
    @Transactional
    Map<String, Object> generateOrder(OrderParam orderParam);
	
	
	/**
	 * 匿名（未登录）用户下单。
	 * 下单流程考虑将促销信息（skustock单品促销、ladder打折促销、fullreduction满减促销）与product信息放到一起，
	 * 所以购物车商品信息可直接计算出应付款金额，不再需要确认订单流程，直接提交订单，由后台返回含应付金额的加密数据
	 * 请求支付
	 */
    @Transactional
    Map<String, Object> generateGuestOrder(GuestOrderParam guestOrderParam);

    /**
     * 支付成功后的回调
     */
    @Transactional
    Integer paySuccess(Long orderId, Integer payType);

    /**
     * 自动取消超时订单
     */
    @Transactional
    Integer cancelTimeOutOrder();

    /**
     * 取消单个超时订单
     */
    @Transactional
    void cancelOrder(Long orderId);

    /**
     * 发送延迟消息取消订单
     */
    void sendDelayMessageCancelOrder(Long orderId);

    /**
     * 确认收货
     */
    void confirmReceiveOrder(Long orderId);

    /**
     * 分页获取用户订单
     */
    CommonPage<OmsOrderDetail> list(Integer status, Integer pageNum, Integer pageSize);

    /**
     * 根据订单ID获取订单详情
     */
    OmsOrderDetail detail(Long orderId);

    /**
     * 用户根据订单ID删除订单
     */
    void deleteOrder(Long orderId);
}
