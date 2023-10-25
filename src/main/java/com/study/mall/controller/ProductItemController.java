package com.study.mall.controller;

import com.study.mall.items.ProductComposite;
import com.study.mall.service.ProductItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author peng
 * @version 1.0
 * @description 商品类目控制层
 * @date 2023/10/25 22:13
 */
@RestController
@RequestMapping("/product")
public class ProductItemController {
    @Autowired
    private ProductItemService productItemService;

    /**
     * 商品类目查询
     * @return
     */
    @GetMapping("/fetchAllItems")
    private ProductComposite fetchAllItems() {
        return productItemService.fetchAllItems();
    }
}
