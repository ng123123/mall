package com.macro.mall.portal.controller2;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.portal.domain2.MemberCartItem;
import com.macro.mall.portal.domain.CartProduct;
//import com.macro.mall.portal.domain.CartPromotionItem;
import com.macro.mall.portal.service2.MemberCartService;
import com.macro.mall.portal.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// cnr
import com.macro.mall.portal.domain2.MemberCartPromotionItem;

/*************************************************

Desc: 购物车管理Controller

Author: ng123123

Date: 2020/10/7

QQ交流群: 892951935

**************************************************/ 

@Controller
@Api(tags = "MemberCartController", description = "购物车管理")
@RequestMapping("/cart2")
public class MemberCartController {
    @Autowired
    private MemberCartService cartService;
    @Autowired
    private UmsMemberService memberService;

    @ApiOperation("添加商品到购物车")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@RequestBody List<MemberCartItem> cartItems) {
        Map<String, MemberCartItem> cartItemMap = cartService.update(cartItems);
        if (cartItemMap != null) {
            return CommonResult.success(cartItemMap);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取某个会员的购物车列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Map<String, MemberCartItem>> list() {
        Map<String, MemberCartItem> cartItemMap = cartService.list(memberService.getCurrentMember().getId());
        return CommonResult.success(cartItemMap);
    }

    @ApiOperation("获取某个会员的购物车列表,包括促销信息")
    @RequestMapping(value = "/list/promotion", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<List<MemberCartPromotionItem>> listPromotion(@RequestBody(required = false) List<MemberCartItem> cartItems) {
		for (MemberCartItem cartItem : cartItems) {
			System.out.println( cartItem.getKey() );
		}
        List<MemberCartPromotionItem> cartPromotionItemList = cartService.listPromotionByItems(cartItems);
        return CommonResult.success(cartPromotionItemList);
    }

	/*
    @ApiOperation("修改购物车中某个商品的数量")
    @RequestMapping(value = "/update/quantity", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult updateQuantity(@RequestParam Long id,
                                       @RequestParam Integer quantity) {
        int count = cartService.updateQuantity(id, memberService.getCurrentMember().getId(), quantity);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }*/

    @ApiOperation("获取购物车中某个商品的规格,用于重选规格")
    @RequestMapping(value = "/getProduct/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CartProduct> getCartProduct(@PathVariable Long productId) {
        CartProduct cartProduct = cartService.getCartProduct(productId);
        return CommonResult.success(cartProduct);
    }

    @ApiOperation("修改购物车中商品的规格")
    @RequestMapping(value = "/update/attr", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateAttr(@RequestBody MemberCartItem cartItem) {
        int count = cartService.updateAttr(cartItem);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

	
    @ApiOperation("删除购物车中的某个商品")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestParam("ids") List<String> cartItemKeys) {
        cartService.delete(memberService.getCurrentMember().getId(), cartItemKeys);
        return CommonResult.success("");
    }

    @ApiOperation("清空购物车")
    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult clear() {
        int count = cartService.clear(memberService.getCurrentMember().getId());
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
