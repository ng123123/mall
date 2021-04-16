package com.macro.mall.portal.repository2;

//import com.macro.mall.portal.repository2.MemberCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/*************************************************

Desc: Mongo实现的购物车Repository

Author: ng123123

Date: 2020/10/6

QQ交流群: 892951935

**************************************************/ 

public interface MemberCartRepository extends MongoRepository<MemberCart,String> {
    MemberCart findByMemberId(Long memberId);
    //int deleteByMemberIdAndBrandId(Long memberId,Long brandId);
    //Page<OmsMemberCart> findByMemberId(Long memberId, Pageable pageable);
    //void deleteAllByMemberId(Long memberId);
}
