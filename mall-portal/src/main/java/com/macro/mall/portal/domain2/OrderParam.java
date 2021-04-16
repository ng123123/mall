package com.macro.mall.portal.domain2;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

// cnr
import com.macro.mall.model.UmsMemberReceiveAddress;

/*************************************************

Desc: 生成订单时传入的参数

Author: ng123123

Date: 2020/10/13

QQ交流群: 892951935

**************************************************/ 

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderParam {
    //@ApiModelProperty("收货地址ID")
    //private Long memberReceiveAddressId;
	@ApiModelProperty("收货地址详情")
	private UmsMemberReceiveAddress address;
    @ApiModelProperty("优惠券ID")
    private Long couponId;
    @ApiModelProperty("使用的积分数")
    private Integer useIntegration;
    @ApiModelProperty("支付方式")
    private Integer payType;
    @ApiModelProperty("被选中的购物车商品ID")
    private List<String> cartItemKeys;
}
