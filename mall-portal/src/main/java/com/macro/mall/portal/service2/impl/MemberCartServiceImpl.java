package com.macro.mall.portal.service2.impl;

import cn.hutool.core.collection.CollUtil;
//import com.macro.mall.mapper.OmsCartItemMapper;
//import com.macro.mall.model.OmsCartItem;
//mport com.macro.mall.model.OmsCartItemExample;
import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.dao.PortalProductDao;
import com.macro.mall.portal.domain.CartProduct;
//import com.macro.mall.portal.domain.CartPromotionItem;
//import com.macro.mall.portal.service.OmsCartItemService;
//import com.macro.mall.portal.service.OmsPromotionService;
import com.macro.mall.portal.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.util.stream.Collectors;

// cnr
import com.macro.mall.portal.service2.MemberCartService;
import com.macro.mall.portal.service2.MemberPromotionService;
import com.macro.mall.portal.repository2.MemberCartRepository;
import com.macro.mall.portal.repository2.MemberCart;
import com.macro.mall.portal.domain2.MemberCartItem;
import com.macro.mall.portal.domain2.MemberCartPromotionItem;
import java.util.Map;
import java.util.HashMap;

/*************************************************

Desc: 购物车管理Service实现类（Mongo版购物车）

Author: ng123123

Date: 2020/10/7

QQ交流群: 892951935

**************************************************/ 

@Service
public class MemberCartServiceImpl implements MemberCartService {
    //@Autowired
    //private OmsCartItemMapper cartItemMapper;
	
	@Autowired
	private MemberCartRepository cartRepository;
    @Autowired
    private PortalProductDao productDao;
    @Autowired
    private MemberPromotionService promotionService;
    @Autowired
    private UmsMemberService memberService;

	// 这个函数没有对用户提供的数据进行验证，所以如果用户恶意传递信息可能会导致一定问题，尤其是下单时一定要按照购物车的productId和productSkuId从数据库提取价格等信息，而不能使用购物车的数据
    @Override
    public Map<String, MemberCartItem> update(List<MemberCartItem> cartItems) {
		// 获取当前用户购物车物品信息
        UmsMember currentMember =memberService.getCurrentMember();
		MemberCart memberCart = cartRepository.findByMemberId(currentMember.getId());
		if (memberCart==null) {
			memberCart = new MemberCart(currentMember.getId());
		} else {
		}
		Map<String, MemberCartItem> cartItemMap = memberCart.getMemberCartItemMap();
		// 变更购物车物品信息
		for (MemberCartItem cartItem : cartItems) {
			cartItem.setMemberId(currentMember.getId());
			cartItem.setMemberNickname(currentMember.getNickname());
			
			MemberCartItem existCartItem = cartItemMap.get(cartItem.getKey());
			if (existCartItem == null) {
				cartItem.setCreateDate(new Date());
				cartItemMap.put(cartItem.getKey(), cartItem);
			} else {
				if (cartItem.getQuantity() > 0) {
					existCartItem.setModifyDate(new Date());
					existCartItem.setQuantity(cartItem.getQuantity());
				} else {
					cartItemMap.remove(cartItem.getKey());
				}
			}
		}
		cartRepository.save(memberCart);
        return cartItemMap;
    }

    @Override
    public Map<String, MemberCartItem> list(Long memberId) {
        /*OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andMemberIdEqualTo(memberId);
        return cartItemMapper.selectByExample(example);*/
		MemberCart memberCart = cartRepository.findByMemberId(memberId);
		if (memberCart != null)
			return memberCart.getMemberCartItemMap();
		return null;
    }

    @Override
    public List<MemberCartPromotionItem> listPromotionByItemKeys(Long memberId, List<String> cartItemKeys) {
        Map<String, MemberCartItem> cartItemMap = list(memberId);
		List<MemberCartItem> cartItemList = new ArrayList<MemberCartItem>();
		for (String key : cartItemKeys ) {
			MemberCartItem item = cartItemMap.get(key);
			if (item != null)
				cartItemList.add(item);
		}
        List<MemberCartPromotionItem> cartPromotionItemList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(cartItemList)){
            cartPromotionItemList = promotionService.calcCartPromotion(cartItemList);
        }
        return cartPromotionItemList;
    }
	
	@Override
    public List<MemberCartPromotionItem> listPromotionByItems(List<MemberCartItem> cartItems) {
        List<MemberCartPromotionItem> cartPromotionItemList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(cartItems)){
            cartPromotionItemList = promotionService.calcCartPromotion(cartItems);
        }
        return cartPromotionItemList;
    }

	/*
    @Override
    public int updateQuantity(Long id, Long memberId, Integer quantity) {
        MemberCartItem cartItem = new MemberCartItem();
        cartItem.setQuantity(quantity);
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andDeleteStatusEqualTo(0)
                .andIdEqualTo(id).andMemberIdEqualTo(memberId);
        return cartItemMapper.updateByExampleSelective(cartItem, example);
    }*/

    @Override
    public void delete(Long memberId, List<String> cartItemKeys) {
        MemberCart memberCart = cartRepository.findByMemberId(memberId);
		if (memberCart==null) {
			return;
		}
		Map<String, MemberCartItem> cartItemMap = memberCart.getMemberCartItemMap();
		for (String key : cartItemKeys)
			cartItemMap.remove(key);
		cartRepository.save(memberCart);
    }

    @Override
    public CartProduct getCartProduct(Long productId) {
        return productDao.getCartProduct(productId);
    }

    @Override
    public int updateAttr(MemberCartItem cartItem) {/*
        //删除原购物车信息
        MemberCartItem updateCart = new MemberCartItem();
        updateCart.setId(cartItem.getId());
        updateCart.setModifyDate(new Date());
        updateCart.setDeleteStatus(1);
        cartItemMapper.updateByPrimaryKeySelective(updateCart);
        cartItem.setId(null);
        add(cartItem);
        return 1;*/
		update(new ArrayList<MemberCartItem>(){{add(cartItem);}});
		return 1;
    }

    @Override
    public int clear(Long memberId) {/*
        MemberCartItem record = new MemberCartItem();
        record.setDeleteStatus(1);
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andMemberIdEqualTo(memberId);
        return cartItemMapper.updateByExampleSelective(record,example);*/
		UmsMember currentMember =memberService.getCurrentMember();
		MemberCart memberCart = cartRepository.findByMemberId(currentMember.getId());
		if (memberCart==null) {
			return 0;
		}
		Map<String, MemberCartItem> cartItemMap = memberCart.getMemberCartItemMap();
		int count = cartItemMap.size();
		memberCart.setMemberCartItemMap( new HashMap<String, MemberCartItem>() );
		cartRepository.save(memberCart);
        return count;
    }
}
