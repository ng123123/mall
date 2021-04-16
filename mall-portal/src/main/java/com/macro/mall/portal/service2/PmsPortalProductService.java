package com.macro.mall.portal.service2;

//import com.macro.mall.model.PmsProduct;
import com.macro.mall.portal.domain.PmsPortalProductDetail;
import com.macro.mall.portal.domain.PmsProductCategoryNode;

import java.util.List;

// cnr
import com.macro.mall.portal.domain2.PmsProductEx;

/*************************************************

Desc: 前台商品管理Service

Author: ng123123

Date: 2020/10/20

QQ交流群: 892951935

**************************************************/ 

public interface PmsPortalProductService {
    /**
     * 综合搜索商品
     */
    List<PmsProductEx> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort);

    /**
     * 以树形结构获取所有商品分类
     */
    List<PmsProductCategoryNode> categoryTreeList();

    /**
     * 获取前台商品详情
     */
    PmsPortalProductDetail detail(Long id);
}
